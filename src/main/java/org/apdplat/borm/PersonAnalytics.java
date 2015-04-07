/**
 *
 * APDPlat - Application Product Development Platform Copyright (c) 2013, 杨尚川,
 * yang-shangchuan@qq.com
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.apdplat.borm;

import java.io.IOException;

import org.apache.avro.util.Utf8;
import org.apache.gora.mapreduce.GoraMapper;
import org.apache.gora.store.DataStore;
import org.apache.gora.store.DataStoreFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apdplat.borm.generated.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonAnalytics extends Configured implements Tool {
	private static final Logger log = LoggerFactory
			.getLogger(PersonAnalytics.class);

	public static class PersonAnalyticsMapper extends
			GoraMapper<String, Person, Text, LongWritable> {
		private LongWritable one = new LongWritable(1L);

		@Override
		protected void map(String key, Person person, Context context)
				throws IOException, InterruptedException {
			Utf8 age = person.getAge();
			context.write(new Text(age.toString()), one);
		};
	}

	public static class PersonAnalyticsReducer extends
			Reducer<Text, LongWritable, Text, LongWritable> {
		@Override
		protected void reduce(Text key, Iterable<LongWritable> values,
				Context context) throws IOException, InterruptedException {
			long sum = 0L;
			for (LongWritable value : values) {
				sum += value.get();
			}
			context.write(key, new LongWritable(sum));
		};
	}

	public Job createJob(DataStore<String, Person> inStore, int numReducer)
			throws IOException {
		Job job = new Job(getConf());
		job.setJobName("Person Analytics");
		log.info("Creating Hadoop Job: " + job.getJobName());
		job.setNumReduceTasks(numReducer);
		job.setJarByClass(getClass());
		GoraMapper.initMapperJob(job, inStore, Text.class, LongWritable.class,
				PersonAnalyticsMapper.class, true);
		job.setReducerClass(PersonAnalyticsReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		TextOutputFormat
				.setOutputPath(job, new Path("person-analytics-output"));
		return job;
	}

	@Override
	public int run(String[] args) throws Exception {
		DataStore<String, Person> inStore;
		Configuration conf = new Configuration();
		if (args.length == 1) {
			String dataStoreClass = args[0];
			inStore = DataStoreFactory.getDataStore(dataStoreClass,
					String.class, Person.class, conf);
		} else {
			inStore = DataStoreFactory.getDataStore(String.class, Person.class,
					conf);
		}
		Job job = createJob(inStore, 2);
		boolean success = job.waitForCompletion(true);
		inStore.close();
		log.info("PersonAnalytics completed with "
				+ (success ? "success" : "failure"));
		return success ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		int ret = ToolRunner.run(new PersonAnalytics(), args);
		System.exit(ret);
	}
}

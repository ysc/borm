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

package org.apdplat.borm.generated;

import org.apache.avro.Schema;
import org.apache.avro.AvroRuntimeException;
import org.apache.avro.util.Utf8;
import org.apache.gora.persistency.StateManager;
import org.apache.gora.persistency.impl.PersistentBase;
import org.apache.gora.persistency.impl.StateManagerImpl;

@SuppressWarnings("all")
public class Person extends PersistentBase {
  public static final Schema _SCHEMA = Schema.parse("{\"type\":\"record\",\"name\":\"Person\",\"namespace\":\"org.apdplat.demo.gora.generated\",\"fields\":[{\"name\":\"idcard\",\"type\":\"string\"},{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"age\",\"type\":\"string\"}]}");
  public static enum Field {
    IDCARD(0,"idcard"),
    NAME(1,"name"),
    AGE(2,"age"),
    ;
    private int index;
    private String name;
    Field(int index, String name) {this.index=index;this.name=name;}
    public int getIndex() {return index;}
    public String getName() {return name;}
    public String toString() {return name;}
  };
  public static final String[] _ALL_FIELDS = {"idcard","name","age",};
  static {
    PersistentBase.registerFields(Person.class, _ALL_FIELDS);
  }
  private Utf8 idcard;
  private Utf8 name;
  private Utf8 age;
  public Person() {
    this(new StateManagerImpl());
  }
  public Person(StateManager stateManager) {
    super(stateManager);
  }
  public Person newInstance(StateManager stateManager) {
    return new Person(stateManager);
  }
  public Schema getSchema() { return _SCHEMA; }
  public Object get(int _field) {
    switch (_field) {
    case 0: return idcard;
    case 1: return name;
    case 2: return age;
    default: throw new AvroRuntimeException("Bad index");
    }
  }
  @SuppressWarnings(value="unchecked")
  public void put(int _field, Object _value) {
    if(isFieldEqual(_field, _value)) return;
    getStateManager().setDirty(this, _field);
    switch (_field) {
    case 0:idcard = (Utf8)_value; break;
    case 1:name = (Utf8)_value; break;
    case 2:age = (Utf8)_value; break;
    default: throw new AvroRuntimeException("Bad index");
    }
  }
  public Utf8 getIdcard() {
    return (Utf8) get(0);
  }
  public void setIdcard(Utf8 value) {
    put(0, value);
  }
  public Utf8 getName() {
    return (Utf8) get(1);
  }
  public void setName(Utf8 value) {
    put(1, value);
  }
  public Utf8 getAge() {
    return (Utf8) get(2);
  }
  public void setAge(Utf8 value) {
    put(2, value);
  }
}

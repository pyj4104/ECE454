/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package thrift.test;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListTypeVersioningV2 implements org.apache.thrift.TBase<ListTypeVersioningV2, ListTypeVersioningV2._Fields>, java.io.Serializable, Cloneable, Comparable<ListTypeVersioningV2> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ListTypeVersioningV2");

  private static final org.apache.thrift.protocol.TField STRINGS_FIELD_DESC = new org.apache.thrift.protocol.TField("strings", org.apache.thrift.protocol.TType.LIST, (short)1);
  private static final org.apache.thrift.protocol.TField HELLO_FIELD_DESC = new org.apache.thrift.protocol.TField("hello", org.apache.thrift.protocol.TType.STRING, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ListTypeVersioningV2StandardSchemeFactory());
    schemes.put(TupleScheme.class, new ListTypeVersioningV2TupleSchemeFactory());
  }

  public List<String> strings; // required
  public String hello; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    STRINGS((short)1, "strings"),
    HELLO((short)2, "hello");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // STRINGS
          return STRINGS;
        case 2: // HELLO
          return HELLO;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.STRINGS, new org.apache.thrift.meta_data.FieldMetaData("strings", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.HELLO, new org.apache.thrift.meta_data.FieldMetaData("hello", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ListTypeVersioningV2.class, metaDataMap);
  }

  public ListTypeVersioningV2() {
  }

  public ListTypeVersioningV2(
    List<String> strings,
    String hello)
  {
    this();
    this.strings = strings;
    this.hello = hello;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ListTypeVersioningV2(ListTypeVersioningV2 other) {
    if (other.isSetStrings()) {
      List<String> __this__strings = new ArrayList<String>(other.strings);
      this.strings = __this__strings;
    }
    if (other.isSetHello()) {
      this.hello = other.hello;
    }
  }

  public ListTypeVersioningV2 deepCopy() {
    return new ListTypeVersioningV2(this);
  }

  @Override
  public void clear() {
    this.strings = null;
    this.hello = null;
  }

  public int getStringsSize() {
    return (this.strings == null) ? 0 : this.strings.size();
  }

  public java.util.Iterator<String> getStringsIterator() {
    return (this.strings == null) ? null : this.strings.iterator();
  }

  public void addToStrings(String elem) {
    if (this.strings == null) {
      this.strings = new ArrayList<String>();
    }
    this.strings.add(elem);
  }

  public List<String> getStrings() {
    return this.strings;
  }

  public ListTypeVersioningV2 setStrings(List<String> strings) {
    this.strings = strings;
    return this;
  }

  public void unsetStrings() {
    this.strings = null;
  }

  /** Returns true if field strings is set (has been assigned a value) and false otherwise */
  public boolean isSetStrings() {
    return this.strings != null;
  }

  public void setStringsIsSet(boolean value) {
    if (!value) {
      this.strings = null;
    }
  }

  public String getHello() {
    return this.hello;
  }

  public ListTypeVersioningV2 setHello(String hello) {
    this.hello = hello;
    return this;
  }

  public void unsetHello() {
    this.hello = null;
  }

  /** Returns true if field hello is set (has been assigned a value) and false otherwise */
  public boolean isSetHello() {
    return this.hello != null;
  }

  public void setHelloIsSet(boolean value) {
    if (!value) {
      this.hello = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case STRINGS:
      if (value == null) {
        unsetStrings();
      } else {
        setStrings((List<String>)value);
      }
      break;

    case HELLO:
      if (value == null) {
        unsetHello();
      } else {
        setHello((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case STRINGS:
      return getStrings();

    case HELLO:
      return getHello();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case STRINGS:
      return isSetStrings();
    case HELLO:
      return isSetHello();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ListTypeVersioningV2)
      return this.equals((ListTypeVersioningV2)that);
    return false;
  }

  public boolean equals(ListTypeVersioningV2 that) {
    if (that == null)
      return false;

    boolean this_present_strings = true && this.isSetStrings();
    boolean that_present_strings = true && that.isSetStrings();
    if (this_present_strings || that_present_strings) {
      if (!(this_present_strings && that_present_strings))
        return false;
      if (!this.strings.equals(that.strings))
        return false;
    }

    boolean this_present_hello = true && this.isSetHello();
    boolean that_present_hello = true && that.isSetHello();
    if (this_present_hello || that_present_hello) {
      if (!(this_present_hello && that_present_hello))
        return false;
      if (!this.hello.equals(that.hello))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder();

    boolean present_strings = true && (isSetStrings());
    builder.append(present_strings);
    if (present_strings)
      builder.append(strings);

    boolean present_hello = true && (isSetHello());
    builder.append(present_hello);
    if (present_hello)
      builder.append(hello);

    return builder.toHashCode();
  }

  @Override
  public int compareTo(ListTypeVersioningV2 other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetStrings()).compareTo(other.isSetStrings());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStrings()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.strings, other.strings);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetHello()).compareTo(other.isSetHello());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetHello()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.hello, other.hello);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("ListTypeVersioningV2(");
    boolean first = true;

    sb.append("strings:");
    if (this.strings == null) {
      sb.append("null");
    } else {
      sb.append(this.strings);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("hello:");
    if (this.hello == null) {
      sb.append("null");
    } else {
      sb.append(this.hello);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ListTypeVersioningV2StandardSchemeFactory implements SchemeFactory {
    public ListTypeVersioningV2StandardScheme getScheme() {
      return new ListTypeVersioningV2StandardScheme();
    }
  }

  private static class ListTypeVersioningV2StandardScheme extends StandardScheme<ListTypeVersioningV2> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ListTypeVersioningV2 struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // STRINGS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list122 = iprot.readListBegin();
                struct.strings = new ArrayList<String>(_list122.size);
                for (int _i123 = 0; _i123 < _list122.size; ++_i123)
                {
                  String _elem124;
                  _elem124 = iprot.readString();
                  struct.strings.add(_elem124);
                }
                iprot.readListEnd();
              }
              struct.setStringsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // HELLO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.hello = iprot.readString();
              struct.setHelloIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, ListTypeVersioningV2 struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.strings != null) {
        oprot.writeFieldBegin(STRINGS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.strings.size()));
          for (String _iter125 : struct.strings)
          {
            oprot.writeString(_iter125);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.hello != null) {
        oprot.writeFieldBegin(HELLO_FIELD_DESC);
        oprot.writeString(struct.hello);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ListTypeVersioningV2TupleSchemeFactory implements SchemeFactory {
    public ListTypeVersioningV2TupleScheme getScheme() {
      return new ListTypeVersioningV2TupleScheme();
    }
  }

  private static class ListTypeVersioningV2TupleScheme extends TupleScheme<ListTypeVersioningV2> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ListTypeVersioningV2 struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetStrings()) {
        optionals.set(0);
      }
      if (struct.isSetHello()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetStrings()) {
        {
          oprot.writeI32(struct.strings.size());
          for (String _iter126 : struct.strings)
          {
            oprot.writeString(_iter126);
          }
        }
      }
      if (struct.isSetHello()) {
        oprot.writeString(struct.hello);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ListTypeVersioningV2 struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list127 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.strings = new ArrayList<String>(_list127.size);
          for (int _i128 = 0; _i128 < _list127.size; ++_i128)
          {
            String _elem129;
            _elem129 = iprot.readString();
            struct.strings.add(_elem129);
          }
        }
        struct.setStringsIsSet(true);
      }
      if (incoming.get(1)) {
        struct.hello = iprot.readString();
        struct.setHelloIsSet(true);
      }
    }
  }

}


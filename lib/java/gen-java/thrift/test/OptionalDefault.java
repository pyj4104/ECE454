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

public class OptionalDefault implements org.apache.thrift.TBase<OptionalDefault, OptionalDefault._Fields>, java.io.Serializable, Cloneable, Comparable<OptionalDefault> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("OptionalDefault");

  private static final org.apache.thrift.protocol.TField OPT_INT_FIELD_DESC = new org.apache.thrift.protocol.TField("opt_int", org.apache.thrift.protocol.TType.I16, (short)1);
  private static final org.apache.thrift.protocol.TField OPT_STR_FIELD_DESC = new org.apache.thrift.protocol.TField("opt_str", org.apache.thrift.protocol.TType.STRING, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new OptionalDefaultStandardSchemeFactory());
    schemes.put(TupleScheme.class, new OptionalDefaultTupleSchemeFactory());
  }

  public short opt_int; // optional
  public String opt_str; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    OPT_INT((short)1, "opt_int"),
    OPT_STR((short)2, "opt_str");

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
        case 1: // OPT_INT
          return OPT_INT;
        case 2: // OPT_STR
          return OPT_STR;
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
  private static final int __OPT_INT_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.OPT_INT,_Fields.OPT_STR};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.OPT_INT, new org.apache.thrift.meta_data.FieldMetaData("opt_int", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I16)));
    tmpMap.put(_Fields.OPT_STR, new org.apache.thrift.meta_data.FieldMetaData("opt_str", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(OptionalDefault.class, metaDataMap);
  }

  public OptionalDefault() {
    this.opt_int = (short)1234;

    this.opt_str = "default";

  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public OptionalDefault(OptionalDefault other) {
    __isset_bitfield = other.__isset_bitfield;
    this.opt_int = other.opt_int;
    if (other.isSetOpt_str()) {
      this.opt_str = other.opt_str;
    }
  }

  public OptionalDefault deepCopy() {
    return new OptionalDefault(this);
  }

  @Override
  public void clear() {
    this.opt_int = (short)1234;

    this.opt_str = "default";

  }

  public short getOpt_int() {
    return this.opt_int;
  }

  public OptionalDefault setOpt_int(short opt_int) {
    this.opt_int = opt_int;
    setOpt_intIsSet(true);
    return this;
  }

  public void unsetOpt_int() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __OPT_INT_ISSET_ID);
  }

  /** Returns true if field opt_int is set (has been assigned a value) and false otherwise */
  public boolean isSetOpt_int() {
    return EncodingUtils.testBit(__isset_bitfield, __OPT_INT_ISSET_ID);
  }

  public void setOpt_intIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __OPT_INT_ISSET_ID, value);
  }

  public String getOpt_str() {
    return this.opt_str;
  }

  public OptionalDefault setOpt_str(String opt_str) {
    this.opt_str = opt_str;
    return this;
  }

  public void unsetOpt_str() {
    this.opt_str = null;
  }

  /** Returns true if field opt_str is set (has been assigned a value) and false otherwise */
  public boolean isSetOpt_str() {
    return this.opt_str != null;
  }

  public void setOpt_strIsSet(boolean value) {
    if (!value) {
      this.opt_str = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case OPT_INT:
      if (value == null) {
        unsetOpt_int();
      } else {
        setOpt_int((Short)value);
      }
      break;

    case OPT_STR:
      if (value == null) {
        unsetOpt_str();
      } else {
        setOpt_str((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case OPT_INT:
      return Short.valueOf(getOpt_int());

    case OPT_STR:
      return getOpt_str();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case OPT_INT:
      return isSetOpt_int();
    case OPT_STR:
      return isSetOpt_str();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof OptionalDefault)
      return this.equals((OptionalDefault)that);
    return false;
  }

  public boolean equals(OptionalDefault that) {
    if (that == null)
      return false;

    boolean this_present_opt_int = true && this.isSetOpt_int();
    boolean that_present_opt_int = true && that.isSetOpt_int();
    if (this_present_opt_int || that_present_opt_int) {
      if (!(this_present_opt_int && that_present_opt_int))
        return false;
      if (this.opt_int != that.opt_int)
        return false;
    }

    boolean this_present_opt_str = true && this.isSetOpt_str();
    boolean that_present_opt_str = true && that.isSetOpt_str();
    if (this_present_opt_str || that_present_opt_str) {
      if (!(this_present_opt_str && that_present_opt_str))
        return false;
      if (!this.opt_str.equals(that.opt_str))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder();

    boolean present_opt_int = true && (isSetOpt_int());
    builder.append(present_opt_int);
    if (present_opt_int)
      builder.append(opt_int);

    boolean present_opt_str = true && (isSetOpt_str());
    builder.append(present_opt_str);
    if (present_opt_str)
      builder.append(opt_str);

    return builder.toHashCode();
  }

  @Override
  public int compareTo(OptionalDefault other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetOpt_int()).compareTo(other.isSetOpt_int());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOpt_int()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.opt_int, other.opt_int);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOpt_str()).compareTo(other.isSetOpt_str());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOpt_str()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.opt_str, other.opt_str);
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
    StringBuilder sb = new StringBuilder("OptionalDefault(");
    boolean first = true;

    if (isSetOpt_int()) {
      sb.append("opt_int:");
      sb.append(this.opt_int);
      first = false;
    }
    if (isSetOpt_str()) {
      if (!first) sb.append(", ");
      sb.append("opt_str:");
      if (this.opt_str == null) {
        sb.append("null");
      } else {
        sb.append(this.opt_str);
      }
      first = false;
    }
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class OptionalDefaultStandardSchemeFactory implements SchemeFactory {
    public OptionalDefaultStandardScheme getScheme() {
      return new OptionalDefaultStandardScheme();
    }
  }

  private static class OptionalDefaultStandardScheme extends StandardScheme<OptionalDefault> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, OptionalDefault struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // OPT_INT
            if (schemeField.type == org.apache.thrift.protocol.TType.I16) {
              struct.opt_int = iprot.readI16();
              struct.setOpt_intIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // OPT_STR
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.opt_str = iprot.readString();
              struct.setOpt_strIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, OptionalDefault struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetOpt_int()) {
        oprot.writeFieldBegin(OPT_INT_FIELD_DESC);
        oprot.writeI16(struct.opt_int);
        oprot.writeFieldEnd();
      }
      if (struct.opt_str != null) {
        if (struct.isSetOpt_str()) {
          oprot.writeFieldBegin(OPT_STR_FIELD_DESC);
          oprot.writeString(struct.opt_str);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class OptionalDefaultTupleSchemeFactory implements SchemeFactory {
    public OptionalDefaultTupleScheme getScheme() {
      return new OptionalDefaultTupleScheme();
    }
  }

  private static class OptionalDefaultTupleScheme extends TupleScheme<OptionalDefault> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, OptionalDefault struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetOpt_int()) {
        optionals.set(0);
      }
      if (struct.isSetOpt_str()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetOpt_int()) {
        oprot.writeI16(struct.opt_int);
      }
      if (struct.isSetOpt_str()) {
        oprot.writeString(struct.opt_str);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, OptionalDefault struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.opt_int = iprot.readI16();
        struct.setOpt_intIsSet(true);
      }
      if (incoming.get(1)) {
        struct.opt_str = iprot.readString();
        struct.setOpt_strIsSet(true);
      }
    }
  }

}


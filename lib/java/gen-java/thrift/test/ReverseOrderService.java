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

public class ReverseOrderService {

  public interface Iface {

    public void myMethod(String first, short second, int third, long fourth) throws org.apache.thrift.TException;

  }

  public interface AsyncIface {

    public void myMethod(String first, short second, int third, long fourth, org.apache.thrift.async.AsyncMethodCallback resultHandler) throws org.apache.thrift.TException;

  }

  public static class Client extends org.apache.thrift.TServiceClient implements Iface {
    public static class Factory implements org.apache.thrift.TServiceClientFactory<Client> {
      public Factory() {}
      public Client getClient(org.apache.thrift.protocol.TProtocol prot) {
        return new Client(prot);
      }
      public Client getClient(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
        return new Client(iprot, oprot);
      }
    }

    public Client(org.apache.thrift.protocol.TProtocol prot)
    {
      super(prot, prot);
    }

    public Client(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
      super(iprot, oprot);
    }

    public void myMethod(String first, short second, int third, long fourth) throws org.apache.thrift.TException
    {
      send_myMethod(first, second, third, fourth);
      recv_myMethod();
    }

    public void send_myMethod(String first, short second, int third, long fourth) throws org.apache.thrift.TException
    {
      myMethod_args args = new myMethod_args();
      args.setFirst(first);
      args.setSecond(second);
      args.setThird(third);
      args.setFourth(fourth);
      sendBase("myMethod", args);
    }

    public void recv_myMethod() throws org.apache.thrift.TException
    {
      myMethod_result result = new myMethod_result();
      receiveBase(result, "myMethod");
      return;
    }

  }
  public static class AsyncClient extends org.apache.thrift.async.TAsyncClient implements AsyncIface {
    public static class Factory implements org.apache.thrift.async.TAsyncClientFactory<AsyncClient> {
      private org.apache.thrift.async.TAsyncClientManager clientManager;
      private org.apache.thrift.protocol.TProtocolFactory protocolFactory;
      public Factory(org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.protocol.TProtocolFactory protocolFactory) {
        this.clientManager = clientManager;
        this.protocolFactory = protocolFactory;
      }
      public AsyncClient getAsyncClient(org.apache.thrift.transport.TNonblockingTransport transport) {
        return new AsyncClient(protocolFactory, clientManager, transport);
      }
    }

    public AsyncClient(org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.transport.TNonblockingTransport transport) {
      super(protocolFactory, clientManager, transport);
    }

    public void myMethod(String first, short second, int third, long fourth, org.apache.thrift.async.AsyncMethodCallback resultHandler) throws org.apache.thrift.TException {
      checkReady();
      myMethod_call method_call = new myMethod_call(first, second, third, fourth, resultHandler, this, ___protocolFactory, ___transport);
      this.___currentMethod = method_call;
      ___manager.call(method_call);
    }

    public static class myMethod_call extends org.apache.thrift.async.TAsyncMethodCall {
      private String first;
      private short second;
      private int third;
      private long fourth;
      public myMethod_call(String first, short second, int third, long fourth, org.apache.thrift.async.AsyncMethodCallback resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
        super(client, protocolFactory, transport, resultHandler, false);
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
      }

      public void write_args(org.apache.thrift.protocol.TProtocol prot) throws org.apache.thrift.TException {
        prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("myMethod", org.apache.thrift.protocol.TMessageType.CALL, 0));
        myMethod_args args = new myMethod_args();
        args.setFirst(first);
        args.setSecond(second);
        args.setThird(third);
        args.setFourth(fourth);
        args.write(prot);
        prot.writeMessageEnd();
      }

      public void getResult() throws org.apache.thrift.TException {
        if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
          throw new IllegalStateException("Method call not finished!");
        }
        org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
        org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
        (new Client(prot)).recv_myMethod();
      }
    }

  }

  public static class Processor<I extends Iface> extends org.apache.thrift.TBaseProcessor<I> implements org.apache.thrift.TProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class.getName());
    public Processor(I iface) {
      super(iface, getProcessMap(new HashMap<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>>()));
    }

    protected Processor(I iface, Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends Iface> Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> getProcessMap(Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
      processMap.put("myMethod", new myMethod());
      return processMap;
    }

    public static class myMethod<I extends Iface> extends org.apache.thrift.ProcessFunction<I, myMethod_args> {
      public myMethod() {
        super("myMethod");
      }

      public myMethod_args getEmptyArgsInstance() {
        return new myMethod_args();
      }

      protected boolean isOneway() {
        return false;
      }

      public myMethod_result getResult(I iface, myMethod_args args) throws org.apache.thrift.TException {
        myMethod_result result = new myMethod_result();
        iface.myMethod(args.first, args.second, args.third, args.fourth);
        return result;
      }
    }

  }

  public static class AsyncProcessor<I extends AsyncIface> extends org.apache.thrift.TBaseAsyncProcessor<I> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncProcessor.class.getName());
    public AsyncProcessor(I iface) {
      super(iface, getProcessMap(new HashMap<String, org.apache.thrift.AsyncProcessFunction<I, ? extends org.apache.thrift.TBase, ?>>()));
    }

    protected AsyncProcessor(I iface, Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase, ?>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends AsyncIface> Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase,?>> getProcessMap(Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase, ?>> processMap) {
      processMap.put("myMethod", new myMethod());
      return processMap;
    }

    public static class myMethod<I extends AsyncIface> extends org.apache.thrift.AsyncProcessFunction<I, myMethod_args, Void> {
      public myMethod() {
        super("myMethod");
      }

      public myMethod_args getEmptyArgsInstance() {
        return new myMethod_args();
      }

      public AsyncMethodCallback<Void> getResultHandler(final AsyncFrameBuffer fb, final int seqid) {
        final org.apache.thrift.AsyncProcessFunction fcall = this;
        return new AsyncMethodCallback<Void>() { 
          public void onComplete(Void o) {
            myMethod_result result = new myMethod_result();
            try {
              fcall.sendResponse(fb,result, org.apache.thrift.protocol.TMessageType.REPLY,seqid);
              return;
            } catch (Exception e) {
              LOGGER.error("Exception writing to internal frame buffer", e);
            }
            fb.close();
          }
          public void onError(Exception e) {
            byte msgType = org.apache.thrift.protocol.TMessageType.REPLY;
            org.apache.thrift.TBase msg;
            myMethod_result result = new myMethod_result();
            {
              msgType = org.apache.thrift.protocol.TMessageType.EXCEPTION;
              msg = (org.apache.thrift.TBase)new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.INTERNAL_ERROR, e.getMessage());
            }
            try {
              fcall.sendResponse(fb,msg,msgType,seqid);
              return;
            } catch (Exception ex) {
              LOGGER.error("Exception writing to internal frame buffer", ex);
            }
            fb.close();
          }
        };
      }

      protected boolean isOneway() {
        return false;
      }

      public void start(I iface, myMethod_args args, org.apache.thrift.async.AsyncMethodCallback<Void> resultHandler) throws TException {
        iface.myMethod(args.first, args.second, args.third, args.fourth,resultHandler);
      }
    }

  }

  public static class myMethod_args implements org.apache.thrift.TBase<myMethod_args, myMethod_args._Fields>, java.io.Serializable, Cloneable, Comparable<myMethod_args>   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("myMethod_args");

    private static final org.apache.thrift.protocol.TField FIRST_FIELD_DESC = new org.apache.thrift.protocol.TField("first", org.apache.thrift.protocol.TType.STRING, (short)4);
    private static final org.apache.thrift.protocol.TField SECOND_FIELD_DESC = new org.apache.thrift.protocol.TField("second", org.apache.thrift.protocol.TType.I16, (short)3);
    private static final org.apache.thrift.protocol.TField THIRD_FIELD_DESC = new org.apache.thrift.protocol.TField("third", org.apache.thrift.protocol.TType.I32, (short)2);
    private static final org.apache.thrift.protocol.TField FOURTH_FIELD_DESC = new org.apache.thrift.protocol.TField("fourth", org.apache.thrift.protocol.TType.I64, (short)1);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new myMethod_argsStandardSchemeFactory());
      schemes.put(TupleScheme.class, new myMethod_argsTupleSchemeFactory());
    }

    public String first; // required
    public short second; // required
    public int third; // required
    public long fourth; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      FIRST((short)4, "first"),
      SECOND((short)3, "second"),
      THIRD((short)2, "third"),
      FOURTH((short)1, "fourth");

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
          case 4: // FIRST
            return FIRST;
          case 3: // SECOND
            return SECOND;
          case 2: // THIRD
            return THIRD;
          case 1: // FOURTH
            return FOURTH;
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
    private static final int __SECOND_ISSET_ID = 0;
    private static final int __THIRD_ISSET_ID = 1;
    private static final int __FOURTH_ISSET_ID = 2;
    private byte __isset_bitfield = 0;
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.FIRST, new org.apache.thrift.meta_data.FieldMetaData("first", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
      tmpMap.put(_Fields.SECOND, new org.apache.thrift.meta_data.FieldMetaData("second", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I16)));
      tmpMap.put(_Fields.THIRD, new org.apache.thrift.meta_data.FieldMetaData("third", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
      tmpMap.put(_Fields.FOURTH, new org.apache.thrift.meta_data.FieldMetaData("fourth", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(myMethod_args.class, metaDataMap);
    }

    public myMethod_args() {
    }

    public myMethod_args(
      String first,
      short second,
      int third,
      long fourth)
    {
      this();
      this.first = first;
      this.second = second;
      setSecondIsSet(true);
      this.third = third;
      setThirdIsSet(true);
      this.fourth = fourth;
      setFourthIsSet(true);
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public myMethod_args(myMethod_args other) {
      __isset_bitfield = other.__isset_bitfield;
      if (other.isSetFirst()) {
        this.first = other.first;
      }
      this.second = other.second;
      this.third = other.third;
      this.fourth = other.fourth;
    }

    public myMethod_args deepCopy() {
      return new myMethod_args(this);
    }

    @Override
    public void clear() {
      this.first = null;
      setSecondIsSet(false);
      this.second = 0;
      setThirdIsSet(false);
      this.third = 0;
      setFourthIsSet(false);
      this.fourth = 0;
    }

    public String getFirst() {
      return this.first;
    }

    public myMethod_args setFirst(String first) {
      this.first = first;
      return this;
    }

    public void unsetFirst() {
      this.first = null;
    }

    /** Returns true if field first is set (has been assigned a value) and false otherwise */
    public boolean isSetFirst() {
      return this.first != null;
    }

    public void setFirstIsSet(boolean value) {
      if (!value) {
        this.first = null;
      }
    }

    public short getSecond() {
      return this.second;
    }

    public myMethod_args setSecond(short second) {
      this.second = second;
      setSecondIsSet(true);
      return this;
    }

    public void unsetSecond() {
      __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SECOND_ISSET_ID);
    }

    /** Returns true if field second is set (has been assigned a value) and false otherwise */
    public boolean isSetSecond() {
      return EncodingUtils.testBit(__isset_bitfield, __SECOND_ISSET_ID);
    }

    public void setSecondIsSet(boolean value) {
      __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SECOND_ISSET_ID, value);
    }

    public int getThird() {
      return this.third;
    }

    public myMethod_args setThird(int third) {
      this.third = third;
      setThirdIsSet(true);
      return this;
    }

    public void unsetThird() {
      __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __THIRD_ISSET_ID);
    }

    /** Returns true if field third is set (has been assigned a value) and false otherwise */
    public boolean isSetThird() {
      return EncodingUtils.testBit(__isset_bitfield, __THIRD_ISSET_ID);
    }

    public void setThirdIsSet(boolean value) {
      __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __THIRD_ISSET_ID, value);
    }

    public long getFourth() {
      return this.fourth;
    }

    public myMethod_args setFourth(long fourth) {
      this.fourth = fourth;
      setFourthIsSet(true);
      return this;
    }

    public void unsetFourth() {
      __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __FOURTH_ISSET_ID);
    }

    /** Returns true if field fourth is set (has been assigned a value) and false otherwise */
    public boolean isSetFourth() {
      return EncodingUtils.testBit(__isset_bitfield, __FOURTH_ISSET_ID);
    }

    public void setFourthIsSet(boolean value) {
      __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __FOURTH_ISSET_ID, value);
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case FIRST:
        if (value == null) {
          unsetFirst();
        } else {
          setFirst((String)value);
        }
        break;

      case SECOND:
        if (value == null) {
          unsetSecond();
        } else {
          setSecond((Short)value);
        }
        break;

      case THIRD:
        if (value == null) {
          unsetThird();
        } else {
          setThird((Integer)value);
        }
        break;

      case FOURTH:
        if (value == null) {
          unsetFourth();
        } else {
          setFourth((Long)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case FIRST:
        return getFirst();

      case SECOND:
        return Short.valueOf(getSecond());

      case THIRD:
        return Integer.valueOf(getThird());

      case FOURTH:
        return Long.valueOf(getFourth());

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case FIRST:
        return isSetFirst();
      case SECOND:
        return isSetSecond();
      case THIRD:
        return isSetThird();
      case FOURTH:
        return isSetFourth();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof myMethod_args)
        return this.equals((myMethod_args)that);
      return false;
    }

    public boolean equals(myMethod_args that) {
      if (that == null)
        return false;

      boolean this_present_first = true && this.isSetFirst();
      boolean that_present_first = true && that.isSetFirst();
      if (this_present_first || that_present_first) {
        if (!(this_present_first && that_present_first))
          return false;
        if (!this.first.equals(that.first))
          return false;
      }

      boolean this_present_second = true;
      boolean that_present_second = true;
      if (this_present_second || that_present_second) {
        if (!(this_present_second && that_present_second))
          return false;
        if (this.second != that.second)
          return false;
      }

      boolean this_present_third = true;
      boolean that_present_third = true;
      if (this_present_third || that_present_third) {
        if (!(this_present_third && that_present_third))
          return false;
        if (this.third != that.third)
          return false;
      }

      boolean this_present_fourth = true;
      boolean that_present_fourth = true;
      if (this_present_fourth || that_present_fourth) {
        if (!(this_present_fourth && that_present_fourth))
          return false;
        if (this.fourth != that.fourth)
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      HashCodeBuilder builder = new HashCodeBuilder();

      boolean present_first = true && (isSetFirst());
      builder.append(present_first);
      if (present_first)
        builder.append(first);

      boolean present_second = true;
      builder.append(present_second);
      if (present_second)
        builder.append(second);

      boolean present_third = true;
      builder.append(present_third);
      if (present_third)
        builder.append(third);

      boolean present_fourth = true;
      builder.append(present_fourth);
      if (present_fourth)
        builder.append(fourth);

      return builder.toHashCode();
    }

    @Override
    public int compareTo(myMethod_args other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;

      lastComparison = Boolean.valueOf(isSetFirst()).compareTo(other.isSetFirst());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetFirst()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.first, other.first);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetSecond()).compareTo(other.isSetSecond());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetSecond()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.second, other.second);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetThird()).compareTo(other.isSetThird());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetThird()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.third, other.third);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetFourth()).compareTo(other.isSetFourth());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetFourth()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fourth, other.fourth);
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
      StringBuilder sb = new StringBuilder("myMethod_args(");
      boolean first = true;

      sb.append("first:");
      if (this.first == null) {
        sb.append("null");
      } else {
        sb.append(this.first);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("second:");
      sb.append(this.second);
      first = false;
      if (!first) sb.append(", ");
      sb.append("third:");
      sb.append(this.third);
      first = false;
      if (!first) sb.append(", ");
      sb.append("fourth:");
      sb.append(this.fourth);
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
        // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
        __isset_bitfield = 0;
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class myMethod_argsStandardSchemeFactory implements SchemeFactory {
      public myMethod_argsStandardScheme getScheme() {
        return new myMethod_argsStandardScheme();
      }
    }

    private static class myMethod_argsStandardScheme extends StandardScheme<myMethod_args> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, myMethod_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 4: // FIRST
              if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                struct.first = iprot.readString();
                struct.setFirstIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 3: // SECOND
              if (schemeField.type == org.apache.thrift.protocol.TType.I16) {
                struct.second = iprot.readI16();
                struct.setSecondIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 2: // THIRD
              if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                struct.third = iprot.readI32();
                struct.setThirdIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 1: // FOURTH
              if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
                struct.fourth = iprot.readI64();
                struct.setFourthIsSet(true);
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

      public void write(org.apache.thrift.protocol.TProtocol oprot, myMethod_args struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        oprot.writeFieldBegin(FOURTH_FIELD_DESC);
        oprot.writeI64(struct.fourth);
        oprot.writeFieldEnd();
        oprot.writeFieldBegin(THIRD_FIELD_DESC);
        oprot.writeI32(struct.third);
        oprot.writeFieldEnd();
        oprot.writeFieldBegin(SECOND_FIELD_DESC);
        oprot.writeI16(struct.second);
        oprot.writeFieldEnd();
        if (struct.first != null) {
          oprot.writeFieldBegin(FIRST_FIELD_DESC);
          oprot.writeString(struct.first);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class myMethod_argsTupleSchemeFactory implements SchemeFactory {
      public myMethod_argsTupleScheme getScheme() {
        return new myMethod_argsTupleScheme();
      }
    }

    private static class myMethod_argsTupleScheme extends TupleScheme<myMethod_args> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, myMethod_args struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        BitSet optionals = new BitSet();
        if (struct.isSetFirst()) {
          optionals.set(0);
        }
        if (struct.isSetSecond()) {
          optionals.set(1);
        }
        if (struct.isSetThird()) {
          optionals.set(2);
        }
        if (struct.isSetFourth()) {
          optionals.set(3);
        }
        oprot.writeBitSet(optionals, 4);
        if (struct.isSetFirst()) {
          oprot.writeString(struct.first);
        }
        if (struct.isSetSecond()) {
          oprot.writeI16(struct.second);
        }
        if (struct.isSetThird()) {
          oprot.writeI32(struct.third);
        }
        if (struct.isSetFourth()) {
          oprot.writeI64(struct.fourth);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, myMethod_args struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        BitSet incoming = iprot.readBitSet(4);
        if (incoming.get(0)) {
          struct.first = iprot.readString();
          struct.setFirstIsSet(true);
        }
        if (incoming.get(1)) {
          struct.second = iprot.readI16();
          struct.setSecondIsSet(true);
        }
        if (incoming.get(2)) {
          struct.third = iprot.readI32();
          struct.setThirdIsSet(true);
        }
        if (incoming.get(3)) {
          struct.fourth = iprot.readI64();
          struct.setFourthIsSet(true);
        }
      }
    }

  }

  public static class myMethod_result implements org.apache.thrift.TBase<myMethod_result, myMethod_result._Fields>, java.io.Serializable, Cloneable, Comparable<myMethod_result>   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("myMethod_result");


    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new myMethod_resultStandardSchemeFactory());
      schemes.put(TupleScheme.class, new myMethod_resultTupleSchemeFactory());
    }


    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
;

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
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(myMethod_result.class, metaDataMap);
    }

    public myMethod_result() {
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public myMethod_result(myMethod_result other) {
    }

    public myMethod_result deepCopy() {
      return new myMethod_result(this);
    }

    @Override
    public void clear() {
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof myMethod_result)
        return this.equals((myMethod_result)that);
      return false;
    }

    public boolean equals(myMethod_result that) {
      if (that == null)
        return false;

      return true;
    }

    @Override
    public int hashCode() {
      HashCodeBuilder builder = new HashCodeBuilder();

      return builder.toHashCode();
    }

    @Override
    public int compareTo(myMethod_result other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;

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
      StringBuilder sb = new StringBuilder("myMethod_result(");
      boolean first = true;

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

    private static class myMethod_resultStandardSchemeFactory implements SchemeFactory {
      public myMethod_resultStandardScheme getScheme() {
        return new myMethod_resultStandardScheme();
      }
    }

    private static class myMethod_resultStandardScheme extends StandardScheme<myMethod_result> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, myMethod_result struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, myMethod_result struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class myMethod_resultTupleSchemeFactory implements SchemeFactory {
      public myMethod_resultTupleScheme getScheme() {
        return new myMethod_resultTupleScheme();
      }
    }

    private static class myMethod_resultTupleScheme extends TupleScheme<myMethod_result> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, myMethod_result struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, myMethod_result struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
      }
    }

  }

}

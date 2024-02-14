package com.booking;
import java.time.LocalDateTime;

public class Booking {
    private String customerName;
    private int tableSize;
    private int year;
    private int month;
    private int date;
    private int hour;
    private int minute;

    //this is better, but seems the mu server has some issues when deserialise it:
    /*
    13:45:16.892 [nioEventLoopGroup-4-3] INFO io.muserver.HttpExchange -- Sending a 500 to the client with ErrorID=ERR-b511b78f-d8a7-4835-b2d9-a5f823391d39 for POST http://localhost:8080/api/bookings
    com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDateTime` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling
    at [Source: REDACTED (`StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION` disabled); line: 1, column: 60] (through reference chain: com.booking.Booking["dateTime"])
    at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:67)
    at com.fasterxml.jackson.databind.DeserializationContext.reportBadDefinition(DeserializationContext.java:1887)
    at com.fasterxml.jackson.databind.deser.impl.UnsupportedTypeDeserializer.deserialize(UnsupportedTypeDeserializer.java:48)
    at com.fasterxml.jackson.databind.deser.impl.FieldProperty.deserializeAndSet(FieldProperty.java:138)
    at com.fasterxml.jackson.databind.deser.BeanDeserializer.vanillaDeserialize(BeanDeserializer.java:310)
    at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserialize(BeanDeserializer.java:177)
    at com.fasterxml.jackson.databind.deser.DefaultDeserializationContext.readRootValue(DefaultDeserializationContext.java:342)
    at com.fasterxml.jackson.databind.ObjectReader._bind(ObjectReader.java:2099)
    at com.fasterxml.jackson.databind.ObjectReader.readValue(ObjectReader.java:1249)
    at com.fasterxml.jackson.jaxrs.base.ProviderBase.readFrom(ProviderBase.java:801)
    at io.muserver.rest.JaxRSRequest.proceed(JaxRSRequest.java:348)
    at io.muserver.rest.JaxRSRequest.executeInterceptors(JaxRSRequest.java:317)
    at io.muserver.rest.RestHandler.readRequestEntity(RestHandler.java:369)
    at io.muserver.rest.RestHandler.invokeResourceMethod(RestHandler.java:172)
    at io.muserver.rest.RestHandler.handle(RestHandler.java:135)
    at io.muserver.NettyHandlerAdapter.lambda$onHeaders$0(NettyHandlerAdapter.java:36)
    at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144)
    at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642)
    at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
    at java.base/java.lang.Thread.run(Thread.java:1583)
    */
    //private min;dateTime;

    public Booking() {
        // Default constructor needed for JSON deserialization
    }

    /*
    public Booking(String customerName, int tableSize, LocalDateTime dateTime) {
        this.customerName = customerName;
        this.tableSize = tableSize;
        this.dateTime = dateTime;
    }
    */
    public Booking(String customerName, int tableSize, int year, int month, int date, int hour, int minute) {
        this.customerName = customerName;
        this.tableSize = tableSize;
        this.year = year;
        this.month = month;
        this.date = date;
        this.hour = hour;
        this.minute = minute;
        //this.dateTime = dateTime;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public String getCustomerName() {
        return customerName;
    }

    public int getTableSize() {
        return tableSize;
    }

    /*
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    */

    public int getYear() {
        return year;
    }
    public int getMonth() {
        return month;
    }
    public int getDate() {
        return date;
    }
    public int getHour() {
        return hour;
    }
    public int getMinute() {
        return minute;
    }
}


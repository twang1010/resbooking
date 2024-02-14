package com.booking;

import io.muserver.MuServer;
import io.muserver.MuServerBuilder;
import io.muserver.rest.RestHandlerBuilder;
import org.json.*;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import javax.ws.rs.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RestaurantBookingApi {

    private static final List<Booking> bookings = new ArrayList<>();

    public static void main(String[] args) {
        RestaurantBookingResource restaurantBookingResource = new RestaurantBookingResource();
        MuServer server = MuServerBuilder.httpServer().withHttpPort(8088)
                .addHandler(RestHandlerBuilder.restHandler(restaurantBookingResource)
                        .addCustomReader(new JacksonJaxbJsonProvider()))
                .start();

        System.out.println("booking API: " + server.uri().resolve("/bookings"));
    }

    @Path("/bookings")
    static class RestaurantBookingResource {

        @GET
        @Produces("application/json")
        public String getAllBookings() {
            return new JSONArray(bookings).toString();
        }

        @GET
        @Path("/{customerName}")
        @Produces("application/json")
        public String getBookingByCustomerName(@PathParam("customerName") String customerName) {
            Booking retbooking = null;
            for (Booking booking : bookings) {
                if (booking.getCustomerName().equalsIgnoreCase(customerName)) {
                    retbooking = booking;
                    break;
                }
            }
            return new JSONObject(retbooking).toString();
        }

        @POST
        @Consumes("application/json")
        @Produces("text/plain")
        public String makeBooking(Booking booking) {
            // Check if the time slot is available
            LocalDateTime bookingEnd = booking.getDateTime().plusHours(2);
            for (Booking existingBooking : bookings) {
                LocalDateTime existingBookingEnd = existingBooking.getDateTime().plusHours(2);
                if ((existingBooking.getDateTime().isBefore(bookingEnd) || existingBooking.getDateTime().isEqual(bookingEnd))
                        && (existingBookingEnd.isAfter(booking.getDateTime()) || existingBookingEnd.isEqual(booking.getDateTime()))) {
                    return "Booking failed. Time slot already booked.";
                }
            }
            bookings.add(booking);
            return "Booking made for " + booking.getCustomerName() + " on " + booking.getDateTime() + " for " + booking.getTableSize() + " people.";
        }

        @GET
        @Path("/bookings/by-date/{date}")
        @Produces("application/json")
         public String getBookingsByDate(@PathParam("customerName") LocalDate date) {
            List<Booking> bookingsForDate = new ArrayList<>();
            for (Booking booking : bookings) {
                if (booking.getDateTime().toLocalDate().isEqual(date)) {
                    bookingsForDate.add(booking);
                }
            }
            return new JSONArray(bookingsForDate).toString();
        }
    }

    static class Booking {
        private String customerName;
        private int tableSize;
        private LocalDateTime dateTime;

        public Booking() {
            // Default constructor needed for JSON deserialization
        }

        public Booking(String customerName, int tableSize, LocalDateTime dateTime) {
            this.customerName = customerName;
            this.tableSize = tableSize;
            this.dateTime = dateTime;
        }

        public String getCustomerName() {
            return customerName;
        }

        public int getTableSize() {
            return tableSize;
        }

        public LocalDateTime getDateTime() {
            return dateTime;
        }
    }
}
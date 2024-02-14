package com.booking;

import io.muserver.MuServer;
import io.muserver.MuServerBuilder;
import io.muserver.rest.RestHandlerBuilder;
import org.jetbrains.annotations.NotNull;
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
        MuServer server = MuServerBuilder.httpServer().withHttpPort(8080)
                .addHandler(RestHandlerBuilder.restHandler(restaurantBookingResource)
                        .addCustomReader(new JacksonJaxbJsonProvider()))
                .start();

        System.out.println("booking API: " + server.uri().resolve("/api/bookings"));
    }

    @Path("/api/bookings")
    static class RestaurantBookingResource {

        public RestaurantBookingResource()
        {
            //test data
            Booking booking1 = new Booking("Alice", 2, 2024, 2, 14, 13, 0);
            Booking booking2 = new Booking("Bob", 4, 2024, 2 ,14, 16, 00);
            Booking booking3 = new Booking("John", 4, 2024, 2, 14, 18, 30);
            bookings.add(booking1);
            bookings.add(booking2);
            bookings.add(booking3);
        }

        @GET
        @Produces("application/json")
        public String getAllBookings() {
            return new JSONArray(bookings).toString();
        }

        @GET
        @Path("/{customerName}")
        @Produces("application/json")
        public String getBookingByCustomerName(@PathParam("customerName") String customerName) {
            List<Booking> bookingsForCustomer = new ArrayList<>();
            for (Booking booking : bookings) {
                if (booking.getCustomerName().equalsIgnoreCase(customerName)) {
                    bookingsForCustomer.add(booking);
                }
            }
            return new JSONArray(bookingsForCustomer).toString();
        }

        @POST
        @Consumes("application/json")
        @Produces("text/plain")
        public String makeBooking(@NotNull Booking booking) {
            // Check if the time slot is available
            LocalDateTime bookingStart = bookingDateTime(booking);
            LocalDateTime bookingEnd = bookingStart.plusHours(2);
            for (Booking existingBooking : bookings) {
                LocalDateTime existingBookingStart = bookingDateTime(existingBooking);
                LocalDateTime existingBookingEnd = existingBookingStart.plusHours(2);
                if ((existingBookingStart.isBefore(bookingEnd) || existingBookingStart.isEqual(bookingEnd))
                        && (existingBookingEnd.isAfter(bookingStart) || existingBookingEnd.isEqual(bookingStart))) {
                    return "Booking failed. Time slot already booked.";
                }
            }
            bookings.add(booking);
            return "Booking made for " + booking.getCustomerName() + " on " + bookingDateTime(booking) + " for " + booking.getTableSize() + " people.";
        }

        @GET
        @Path("/by-date/{date}")
        @Produces("application/json")
        public String getBookingsByDate(@PathParam("date") LocalDate date) {
            List<Booking> bookingsForDate = new ArrayList<>();
            for (Booking booking : bookings) {
                if (bookingDateTime(booking).toLocalDate().isEqual(date)) {
                    bookingsForDate.add(booking);
                }
            }
            return new JSONArray(bookingsForDate).toString();
        }

        private LocalDateTime bookingDateTime(Booking booking){
            int bkYear = booking.getYear();
            int bkMonth = booking.getMonth();
            int bkDate = booking.getDate();
            int bkHour = booking.getHour();
            int bkMinute = booking.getMinute();
            LocalDateTime bookingDateTime = LocalDateTime.of(bkYear, bkMonth,bkDate,bkHour, bkMinute, 0,0);
            return bookingDateTime;
        }
    }
}
package com.endpoints;

import com.persistence.Address;
import com.persistence.Country;
import com.persistence.Hotel;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.List;

@Path("hotels")
public class HotelResource implements Resource<Hotel> {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //Datum a jiné obskurní datové typy se převádí pomocí Stringu
    public Response findAllEntities(@QueryParam("checkIn") String checkIn,
                                    @QueryParam("checkOut") String checkOut,
                                    @QueryParam("guests") Integer guests,
                                    @QueryParam("cityId") Long city) {

        try {
            List<Hotel> hotels;

            if (checkIn == null && checkOut == null && city == null && guests == null) {
                hotels = Hotel.listAll();
            } else if (checkIn == null || checkOut == null || city == null || guests == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Missing parameters").build();
            } else {
                LocalDate checkInDate = LocalDate.parse(checkIn, formatter);
                LocalDate checkOutDate = LocalDate.parse(checkOut, formatter);
                hotels = Hotel.findAvailableHotels(checkInDate, checkOutDate, guests, city);
            }
            if (hotels.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(hotels).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
    }

    @Override
    public Response find(Long filter) {
        Hotel hotel = Hotel.findById(filter);
        if (hotel == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(hotel).build();
    }

    @Transactional
    @Override
    public Response create(Hotel hotel) {
        try {
            if(hotel == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Missing parameters").build();
            }
            hotel.address = Address.findById(hotel.addressId);
            hotel.persist();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
        return Response.status(Response.Status.CREATED).entity(hotel).build();
    }

    @Transactional
    @Override
    public Response update(Long id, Hotel hotel) {
        Hotel updateHotel = Hotel.findById(id);

        if (updateHotel == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Hotel not found.").build();
        }

        try {
            updateHotel.address = Address.findById(hotel.addressId);
            updateHotel.name = hotel.name;
            updateHotel.persist();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
        return Response.ok(updateHotel).entity(updateHotel).build();
    }

    @Transactional
    @Override
    public Response delete(Long id) {

        Hotel hotel = Hotel.findById(id);

        if (hotel == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Hotel not found.").build();
        }
        try {
            hotel.delete();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
        return Response.status(Response.Status.OK).entity(hotel).build();
    }
}

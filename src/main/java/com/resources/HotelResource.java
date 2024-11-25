package com.resources;

import com.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.List;

@Path("hotels")
public class HotelResource implements Resource<Hotel> {

    @GET
    @Path("{id}/rooms")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRooms(@PathParam("id") Long hotelId) {
        List<Room> rooms = Hotel.findAllRooms(hotelId);
        if(rooms == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{'msg': 'No room was found.'}")
                    .build();
        }
        return Response.ok(rooms).build();
    }

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
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{'msg': 'Missing parameters.'}")
                        .build();
            } else {
                LocalDate checkInDate = LocalDate.parse(checkIn, formatter);
                LocalDate checkOutDate = LocalDate.parse(checkOut, formatter);
                hotels = Hotel.findAvailableHotels(checkInDate, checkOutDate, guests, city);
            }
            if (hotels.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{'msg': 'No hotel was found.'}")
                        .build();
            }
            return Response.ok(hotels).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{'msg': '" + e.getMessage() + "'}")
                    .build();
        }
    }

    @Override
    public Response find(Long filter) {
        Hotel hotel = Hotel.findById(filter);
        if (hotel == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{'msg': 'Hotel not found.'}")
                    .build();
        }
        return Response.ok(hotel).build();
    }

    @Transactional
    @Override
    public Response create(Hotel hotel) {
        try {
            if(hotel == null){
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{'msg': 'Wrong body.'}")
                        .build();
            }

            Country existingCountry = Country.find("isoCode", hotel.address.city.country.isoCode).firstResult();
            if (existingCountry == null) {
                existingCountry = hotel.address.city.country;
                existingCountry.persist();
            }

            City existingCity = City.find("name = ?1 AND country.id = ?2", hotel.address.city.name, existingCountry.id).firstResult();
            if (existingCity == null) {
                existingCity = hotel.address.city;
                existingCity.country = existingCountry;
                existingCity.persist();
            }

            Address existingAddress = Address.find("name = ?1 AND houseNumber = ?2 AND landRegistryNumber = ?3", hotel.address.name, hotel.address.houseNumber, hotel.address.landRegistryNumber).firstResult();
            if(existingAddress == null){
                existingAddress = hotel.address;
                existingAddress.city = existingCity;
                existingAddress.persist();
            }
            hotel.persist();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{'msg': '" + e.getMessage() + "'}")
                    .build();
        }
        return Response.status(Response.Status.CREATED)
                .entity(hotel)
                .build();
    }

    @Transactional
    @Override
    public Response update(Long id, Hotel hotel) {
        Hotel updateHotel = Hotel.findById(id);

        if (updateHotel == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{'msg': 'Hotel not found.'}")
                    .build();
        }
        try {
            updateHotel.name = hotel.name;
            updateHotel.persist();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{'msg': '" + e.getMessage() + "'}")
                    .build();
        }
        return Response.ok(updateHotel)
                .build();
    }

    @Transactional
    @Override
    public Response delete(Long id) {

        Hotel hotel = Hotel.findById(id);

        if (hotel == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{'msg': 'Hotel not found.'}")
                    .build();
        }
        try {
            hotel.delete();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{'msg': '" + e.getMessage() + "'}")
                    .build();
        }
        return Response.status(Response.Status.OK)
                .entity(hotel)
                .build();
    }
}

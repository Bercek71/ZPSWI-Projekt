package com.endpoints;

import com.persistence.Country;
import com.persistence.Hotel;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("hotels")
public class HotelResource extends PanacheEntity implements Resource<Hotel> {

    @Path("hotels")
    public Response findAllEntities() {
        List<Hotel> hotels = Hotel.listAll();
        if(hotels.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(hotels).build();
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
        try{
            hotel.country = Country.findById(hotel.countryId);
            hotel.persist();
        } catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

    @Transactional
    @Override
    public Response update(Long id, Hotel hotel) {
        Hotel updateHotel = Hotel.findById(id);

        if(updateHotel == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Hotel not found.").build();
        }

        try{
            updateHotel.country = Country.findById(hotel.countryId);
            updateHotel.name = hotel.name;
            updateHotel.persist();

        } catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
        return Response.ok(updateHotel).build();
    }

    @Transactional
    @Override
    public Response delete(Long id) {

        Hotel hotel = Hotel.findById(id);

        if(hotel == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Hotel not found.").build();
        }
        try{
            hotel.delete();
        } catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
        return Response.status(Response.Status.OK).entity(hotel).build();
    }
}

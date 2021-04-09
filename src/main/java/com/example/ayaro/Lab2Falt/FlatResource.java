package com.example.ayaro.Lab2Falt;

import com.example.ayaro.Lab2Falt.models.Flat;
import com.example.ayaro.Lab2Falt.models.Furnish;
import com.example.ayaro.Lab2Falt.models.Transport;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonSyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

@Path("/flats")
public class FlatResource {
    @GET
    @Produces("application/json")
    public Response getFlats(@Context HttpServletRequest httpServletRequest, @Context UriInfo uriInfo) {
        try {
            if (httpServletRequest.getParameterMap().size() == 0) {
                ArrayList<Flat> flats = Service.getAllFlat();
                return Response.ok().entity(flats).build();
            }
            if (checkParametersForFilterSort(httpServletRequest.getParameterMap())) {
                        int count = 0;
                        String offsetStr = httpServletRequest.getParameter("offset");
                        String limitStr = httpServletRequest.getParameter("limit");
                        int offset = (offsetStr == null || offsetStr.isEmpty()) ? -1 : Integer.parseInt(offsetStr);
                        int limit = (limitStr == null || limitStr.isEmpty()) ? -1 : Integer.parseInt(limitStr);
                        if (offset != -1) count++;
                        if (limit != -1) count++;
                        if (httpServletRequest.getParameter("sort") != null) count++;
                        String[] filterFields = new String[httpServletRequest.getParameterMap().size() - count];
                        String[] filterValues = new String[httpServletRequest.getParameterMap().size() - count];
                        int i = 0;
                        for (Map.Entry<String, String[]> entry : httpServletRequest.getParameterMap().entrySet()) {
                            if (!entry.getKey().equals("offset") && !entry.getKey().equals("limit") && !entry.getKey().equals("sort")) {
                                filterFields[i] = entry.getKey();
                                filterValues[i] = entry.getValue()[0];
                                i++;
                            }
                        }

                        String sortFieldsStr = httpServletRequest.getParameter("sort");
                        String[] sortFields = (sortFieldsStr == null || sortFieldsStr.isEmpty()) ? new String[]{} :
                                sortFieldsStr.split(",");
                        ArrayList<Flat> flatsResult = Service.getFlat(filterFields, filterValues,
                                sortFields, limit, offset);

                        return Response.ok().entity(flatsResult).build();
            } else {
                return Response.status(422).build();
            }
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            return Response.status(422).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.status(500).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getFlat(@PathParam("id") long id, @Context HttpServletRequest httpServletRequest){
        if (httpServletRequest.getParameterMap().size() == 0) {
            try {
                Flat flat = Service.getFlatById(id);
                if (flat == null) {
                    return Response.status(404).build();
                }
                return Response.status(200).entity(flat).build();
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                return Response.status(422).build();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return Response.status(500).build();
            }
        }
        return Response.status(400).build();
    }

    @GET
    @Path("/countByHouse")
    @Produces("application/json")
    public Response countByHouse(@Context HttpServletRequest httpServletRequest){
        if (httpServletRequest.getParameterMap().size() != 3) {
            Response.status(422).build();
        }
        try {
            Long year = Long.parseLong(httpServletRequest.getParameter("year"));
            int numberOfLifts = Integer.parseInt(httpServletRequest.getParameter("numberOfLifts"));
            String name = httpServletRequest.getParameter("name");
            int count = Service.countFlatsByHouse(name, year, numberOfLifts);
            return Response.status(200).entity(count).build();
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            return Response.status(422).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.status(500).build();
        }
    }

    @GET
    @Path("/countByTransport")
    @Produces("application/json")
    public Response countByTransport(@Context HttpServletRequest httpServletRequest){
        if (httpServletRequest.getParameterMap().size() != 1) {
            Response.status(422).build();
        }
        try {
            String transport = httpServletRequest.getParameter("transport");
            int count = Service.countFlatsByTransport(transport);
            return Response.status(200).entity(count).build();
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            return Response.status(422).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.status(500).build();
        }
    }

    @POST
    @Produces("application/json")
    public Response createFlat(String body, @Context HttpServletRequest httpServletRequest){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").create();
        try {
            Type type = new TypeToken<Map<String, String>>(){}.getType();
            Map<String, String> paramMap = gson.fromJson(body, type);
            Flat flat = Service.makeFlatFromParams(paramMap);
            flat = Service.addFlat(flat);
            return Response.status(201).entity(flat).build();
        } catch (NumberFormatException | ParseException | JsonSyntaxException e) {
            System.out.println(e.getMessage());
            return Response.status(422).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.status(500).build();
        }
    }


    @POST
    @Path("deleteByRoom")
    @Produces("application/json")
    public Response deleteByRoom(String body, @Context HttpServletRequest httpServletRequest){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").create();
        try {
            Type type = new TypeToken<Map<String, String>>(){}.getType();
            Map<String, String> paramMap = gson.fromJson(body, type);
            if (httpServletRequest.getParameterMap().size() != 1) {
                Response.status(422).build();
            }
            int numberOfRooms = Integer.parseInt(paramMap.get("numberOfRooms"));
            Service.deleteOneByRoom(numberOfRooms);
            return Response.status(200).build();
        } catch (NumberFormatException | JsonSyntaxException e) {
            System.out.println(e.getMessage());
            return Response.status(422).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.status(500).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Produces("application/json")
    public Response updateFlat(String body, @PathParam("id") long id, @Context HttpServletRequest httpServletRequest){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").create();
        try {
            Type type = new TypeToken<Map<String, String>>(){}.getType();
            Map<String, String> paramMap = gson.fromJson(body, type);
            if (httpServletRequest.getParameterMap().size() != 0) {
                    return Response.status(422).build();
            }
            if (hasRedundantParameters(paramMap.keySet()) ||
                        !hasAllRequiredParameters(paramMap.keySet()) ||
                        !validateFields(paramMap)) {
                return Response.status(422).build();
            }
                Flat flat = Service.getFlatById(id);
                flat = Service.updateFlatFromParams(paramMap, flat);
                flat = Service.updateFlat(id, flat);
                return Response.status(200).entity(flat).build();
        } catch (NumberFormatException | JsonSyntaxException e) {
            System.out.println(e.getMessage());
            return Response.status(422).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.status(500).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteFlat(@PathParam("id") long id){
        try {
            if (Service.getFlatById(id) == null) return Response.status(404).build();;
            Service.deleteFlat(id);
            return Response.status(200).build();
        } catch (NumberFormatException | JsonSyntaxException e) {
            System.out.println(e.getMessage());
            return Response.status(422).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.status(500).build();
        }
    }

    private static boolean validateFields(Map<String, String> params){
        try {
            boolean res = Long.parseLong(params.get("coordinateX")) > -484 &&
                    (params.get("height") == null || Long.parseLong(params.get("height")) > 0) &&
                    Integer.parseInt(params.get("numberOfRooms")) > 0 &&
                    Integer.parseInt(params.get("area")) > 0 &&
                    params.get("name") != null && !params.get("name").isEmpty() &&
                    Furnish.getByName(params.get("furnish")) != null &&
                    Transport.getByName(params.get("transport")) != null &&
                    (params.get("year")) != null;
            Double.parseDouble(params.get("coordinateY"));
            return res;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }


    private static boolean hasRedundantParameters(Set<String> params) {
        return params.stream().anyMatch(x -> FLAT_FIELDS_GET.stream()
                .noneMatch(x::equals));
    }

    private static boolean hasAllRequiredParameters(Set<String> params) {
        return FLAT_FIELDS_POST.stream().filter(params::contains).count() == FLAT_FIELDS_POST.size();
    }

    private static boolean checkParams(Map<String, String[]> params, Boolean isGet) {
        try {
            for (Map.Entry<String, String[]> param : params.entrySet()) {
                System.out.println(param.getKey());
                System.out.println(param.getValue()[0]);
                switch (param.getKey()) {
                    case "offset":
                    case "limit":
                    case "sort":
                        if (!isGet) return false;
                        break;
                    case "transport":
                        if (Transport.getByName(param.getValue()[0]) == null) return false;
                        break;
                    case "furnish":
                        if (Furnish.getByName(param.getValue()[0]) == null) return false;
                        break;
                    case "id":
                    case "numberOfRooms":
                    case "numberOfLifts":
                    case "price":
                    case "area":
                        int anumber = Integer.parseInt(param.getValue()[0]);
                        if (anumber < 0) return false;
                        break;
                    case "year":
                    case "height":
                        long hnumber = Long.parseLong(param.getValue()[0]);
                        if (hnumber < 0) return false;
                        break;
                    case "salary":
                        double dnumber1 = Double.parseDouble(param.getValue()[0]);
                        if (dnumber1 < 0) return false;
                        break;
                    case "coordinateX":
                        long dnumber = Long.parseLong(param.getValue()[0]);
                        if ( dnumber > -484) return false;
                        break;
                    case "coordinateY":
                    case "creationDate":
                        break;
                    case "name":
                        break;
                    case "houseName":
                        if (param.getValue()[0] == null ||param.getValue()[0] == "") return false;
                        break;
                    default:
                        return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    private static final ArrayList<String> FLAT_FIELDS_GET =
            new ArrayList<>(Arrays.asList( "name", "coordinateX", "coordinateY", "numberOfRooms", "area", "height",
                    "furnish", "transport", "houseName", "numberOfLifts", "year","id","creationDate", "price"));
    private static final ArrayList<String> FLAT_FIELDS_POST =
            new ArrayList<>(Arrays.asList( "name", "coordinateX", "coordinateY", "numberOfRooms", "area", "height",
                    "furnish", "transport", "houseName", "numberOfLifts", "year", "price"));

    private static boolean hasRedundantFields(String fields) {
        return Arrays.stream(fields.split(","))
                .anyMatch(x -> FLAT_FIELDS_GET.stream()
                        .noneMatch(x::equals)) && !fields.isEmpty();
    }

    private static boolean checkParametersForFilterSort(Map<String, String[]> params) {
        try {
            return (params.get("offset") == null || Integer.parseInt(params.get("offset")[0]) >= 0) &&
                    (params.get("limit") == null || Integer.parseInt(params.get("limit")[0]) >= 0) &&
                    (params.get("sort") == null || !hasRedundantFields(params.get("sort")[0])) &
                            (checkParams(params, true));
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
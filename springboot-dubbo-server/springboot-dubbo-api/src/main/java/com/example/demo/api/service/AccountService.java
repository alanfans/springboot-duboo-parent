package com.example.demo.api.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 */

@Path("accountService") // #1
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML}) // #2
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public interface AccountService {
    void addAmount(Long userId, Integer amount);

    void removeAmount(Long userId, Integer amount);
}

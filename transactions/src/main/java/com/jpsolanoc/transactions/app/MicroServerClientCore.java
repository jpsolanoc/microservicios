package com.jpsolanoc.transactions.app;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jpsolanoc.transactions.dto.CustomerDTO;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MicroServerClientCore {
    private static final String URL_GLOBAL = "http://localhost:8081/core/clientes/listId";

    public MicroServerClientCore() {
        Unirest.config()
                .socketTimeout(0) // Tiempo de espera para la respuesta (0 significa sin límite)
                .connectTimeout(0); // Tiempo de espera para la conexión (0 significa sin límite)
    }

    public List<CustomerDTO> sendPostCliente(List<Long> requestFirma) {
        try {
            Gson gson = new Gson();
            String jsonBody = gson.toJson(requestFirma);
            HttpResponse<String> response = Unirest.post(URL_GLOBAL)
                    .header("Content-Type", "application/json")
                    .body(jsonBody)
                    .asString();
            try {
                return mapJsonToList(response.getBody());
            } catch (IOException e) {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List<CustomerDTO> mapJsonToList(String jsonArray) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<CustomerDTO> clientes = objectMapper.readValue(jsonArray, new TypeReference<List<CustomerDTO>>() {});
        return clientes;
    }
}

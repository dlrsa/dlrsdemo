package com.dlrs.dlrsdemo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/secure")
public class ProxyController {
    @GetMapping("/getSubdivisions")
    public JsonNode getSubdivision() {
        String API_URL = "https://landhub.assam.gov.in/apidemo/index.php/NicApi/getSubdivs";

        System.out.println("INSIDE PROXY");

        String requestPayload = "{\"apikey\":\"bhunaksha\",\"dist_code\":\"07\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<String> entity = new HttpEntity<>(requestPayload, headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

            System.out.println("Response: " + response);

            if (response.getStatusCode() == HttpStatus.OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.getBody());

                JsonNode dataNode = rootNode.path("data");

                if (dataNode.isArray() && dataNode.size() > 0) {
                    System.out.println("Data found: " + dataNode);
                    return dataNode;
                } else {
                    System.out.println("No data found in the 'data' field.");
                    return objectMapper.createObjectNode().put("message", "No data found");
                }
            } else {
                System.out.println("API call failed with status: " + response.getStatusCode());
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.createObjectNode().put("message", "API call failed with status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("Error calling API: " + e.getMessage());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.createObjectNode().put("error", "Error calling API: " + e.getMessage());
        }
    }

    @GetMapping("/getCircles")
    public JsonNode getCircles(@RequestParam String subdivCode) {
        String API_URL = "https://landhub.assam.gov.in/apidemo/index.php/NicApi/getCircles";

        System.out.println("INSIDE PROXY");

        String requestPayload = "{\"apikey\":\"bhunaksha\",\"dist_code\":\"07\", \"subdiv_code\":\"" + subdivCode + "\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requestPayload, headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

            System.out.println("Response: " + response);

            if (response.getStatusCode() == HttpStatus.OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.getBody());

                JsonNode dataNode = rootNode.path("data");

                if (dataNode.isArray() && dataNode.size() > 0) {
                    System.out.println("Data found: " + dataNode);
                    return dataNode;
                } else {
                    System.out.println("No data found in the 'data' field.");
                    return objectMapper.createObjectNode().put("message", "No data found");
                }
            } else {
                System.out.println("API call failed with status: " + response.getStatusCode());
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.createObjectNode().put("message", "API call failed with status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("Error calling API: " + e.getMessage());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.createObjectNode().put("error", "Error calling API: " + e.getMessage());
        }
    }


    @GetMapping("/getMouzas")
    public JsonNode getMouzas(@RequestParam String subdivCode, @RequestParam String circleCode) {
        String API_URL = "https://landhub.assam.gov.in/apidemo/index.php/NicApi/getMouzas";

        System.out.println("INSIDE PROXY");

        String requestPayload = "{\"apikey\":\"bhunaksha\",\"dist_code\":\"07\",\"subdiv_code\":\"" + subdivCode + "\",\"cir_code\":\"" + circleCode + "\"}";

        System.out.println(requestPayload);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requestPayload, headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

            System.out.println("Response: " + response);

            if (response.getStatusCode() == HttpStatus.OK) {
                // Parse the response body
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.getBody());

                JsonNode dataNode = rootNode.path("data");

                if (dataNode.isArray() && dataNode.size() > 0) {
                    System.out.println("Data found: " + dataNode);
                    return dataNode;
                } else {
                    System.out.println("No data found in the 'data' field.");
                    return objectMapper.createObjectNode().put("message", "No data found");
                }
            } else {
                System.out.println("API call failed with status: " + response.getStatusCode());
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.createObjectNode().put("message", "API call failed with status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("Error calling API: " + e.getMessage());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.createObjectNode().put("error", "Error calling API: " + e.getMessage());
        }
    }


    @GetMapping("/getLots")
    public JsonNode getLots(@RequestParam String subdivCode, @RequestParam String circleCode, @RequestParam String mouzaCode) {
        String API_URL = "https://landhub.assam.gov.in/apidemo/index.php/NicApi/getLots";

        System.out.println("INSIDE PROXY");


        String requestPayload = "{\"apikey\":\"bhunaksha\",\"dist_code\":\"07\",\"subdiv_code\":\"" + subdivCode + "\",\"cir_code\":\"" + circleCode + "\",\"mouza_pargona_code\":\"" + mouzaCode + "\"}";

        System.out.println(requestPayload);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requestPayload, headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

            System.out.println("Response: " + response);

            if (response.getStatusCode() == HttpStatus.OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.getBody());

                JsonNode dataNode = rootNode.path("data");

                if (dataNode.isArray() && dataNode.size() > 0) {
                    System.out.println("Data found: " + dataNode);
                    return dataNode;
                } else {
                    System.out.println("No data found in the 'data' field.");
                    return objectMapper.createObjectNode().put("message", "No data found");
                }
            } else {
                System.out.println("API call failed with status: " + response.getStatusCode());
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.createObjectNode().put("message", "API call failed with status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("Error calling API: " + e.getMessage());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.createObjectNode().put("error", "Error calling API: " + e.getMessage());
        }
    }

    @GetMapping("/getVillages")
    public JsonNode getVillages(@RequestParam String subdivCode, @RequestParam String circleCode, @RequestParam String mouzaCode, @RequestParam String lotNo) {
        String API_URL = "https://landhub.assam.gov.in/apidemo/index.php/NicApi/getVillages";

        System.out.println("INSIDE PROXY");

        String requestPayload = "{\"apikey\":\"bhunaksha\",\"dist_code\":\"07\",\"subdiv_code\":\"" + subdivCode + "\",\"cir_code\":\"" + circleCode + "\",\"mouza_pargona_code\":\"" + mouzaCode + "\", \"lot_no\":\"" + lotNo + "\"}";

        System.out.println(requestPayload);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requestPayload, headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

            System.out.println("Response: " + response);

            if (response.getStatusCode() == HttpStatus.OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.getBody());

                JsonNode dataNode = rootNode.path("data");

                if (dataNode.isArray() && dataNode.size() > 0) {
                    System.out.println("Data found: " + dataNode);
                    return dataNode;
                } else {
                    System.out.println("No data found in the 'data' field.");
                    return objectMapper.createObjectNode().put("message", "No data found");
                }
            } else {
                System.out.println("API call failed with status: " + response.getStatusCode());
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.createObjectNode().put("message", "API call failed with status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("Error calling API: " + e.getMessage());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.createObjectNode().put("error", "Error calling API: " + e.getMessage());
        }
    }

    @GetMapping("/getDags")
    public JsonNode getDags(@RequestParam String subdivCode, @RequestParam String circleCode, @RequestParam String mouzaCode, @RequestParam String lotNo, @RequestParam String villageNo) {
        String API_URL = "https://landhub.assam.gov.in/apidemo/index.php/NicApi/getDags";

        System.out.println("INSIDE PROXY");

        String requestPayload = "{\"apikey\":\"bhunaksha\",\"dist_code\":\"07\",\"subdiv_code\":\"" + subdivCode + "\",\"cir_code\":\"" + circleCode + "\",\"mouza_pargona_code\":\"" + mouzaCode + "\", \"lot_no\":\"" + lotNo + "\", \"vill_code\":\"" + villageNo + "\"}";

        System.out.println(requestPayload);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requestPayload, headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

            System.out.println("Response: " + response);

            if (response.getStatusCode() == HttpStatus.OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.getBody());

                JsonNode dataNode = rootNode.path("data");

                if (dataNode.isArray() && dataNode.size() > 0) {
                    System.out.println("Data found: " + dataNode);
                    return dataNode;
                } else {
                    System.out.println("No data found in the 'data' field.");
                    return objectMapper.createObjectNode().put("message", "No data found");
                }
            } else {
                System.out.println("API call failed with status: " + response.getStatusCode());
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.createObjectNode().put("message", "API call failed with status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("Error calling API: " + e.getMessage());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.createObjectNode().put("error", "Error calling API: " + e.getMessage());
        }
    }

    @GetMapping("/getPattadar")
    public JsonNode getPattadar(@RequestParam String subdivCode,
                            @RequestParam String circleCode,
                            @RequestParam String mouzaCode,
                            @RequestParam String lotNo,
                            @RequestParam String villageNo,
                            @RequestParam String dagNo) {
        String API_URL = "https://landhub.assam.gov.in/apidemo/index.php/NicApi/pattadarInformation";

        System.out.println("INSIDE PROXY");

        // Prepare URL-encoded form data
        LinkedMultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("apikey", "bhunaksha");
        formData.add("dist_code", "07");
        formData.add("subdiv_code", subdivCode);
        formData.add("cir_code", circleCode);
        formData.add("mouza_pargona_code", mouzaCode);
        formData.add("lot_no", lotNo);
        formData.add("vill_townprt_code", villageNo);
        formData.add("dag_no", dagNo);

        System.out.println("Form Data: " + formData);

        // Set headers for form data
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<LinkedMultiValueMap<String, String>> entity = new HttpEntity<>(formData, headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

            System.out.println("Response: " + response);

            if (response.getStatusCode() == HttpStatus.OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.getBody());

                JsonNode dataNode = rootNode.path("Pattadar_names");

                if (dataNode.isArray() && dataNode.size() > 0) {
                    System.out.println("Data found: " + dataNode);
                    return dataNode;
                } else {
                    System.out.println("No data found in the 'data' field.");
                    return objectMapper.createObjectNode().put("message", "No data found");
                }
            } else {
                System.out.println("API call failed with status: " + response.getStatusCode());
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.createObjectNode().put("message", "API call failed with status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("Error calling API: " + e.getMessage());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.createObjectNode().put("error", "Error calling API: " + e.getMessage());
        }
    }



}

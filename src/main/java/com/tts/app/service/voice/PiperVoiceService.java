package com.tts.app.service.voice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tts.app.dto.VoiceResponse;
import com.tts.app.exception.TtsException;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PiperVoiceService {

    private final RestClient restClient;

    private final ObjectMapper objectMapper;

    public PiperVoiceService() {

        this.restClient =
                RestClient.builder()
                        .baseUrl(
                                "http://localhost:5000"
                        )
                        .build();

        this.objectMapper =
                new ObjectMapper();
    }

    public List<VoiceResponse> getAvailableVoices() {

        try {

            String response =
                    restClient
                            .get()
                            .uri("/voices")
                            .retrieve()
                            .body(String.class);

            if (
                    response == null ||
                    response.isBlank()
            ) {

                throw new TtsException(
                        "Piper returned no voice information."
                );
            }

            Map<String, Map<String, Object>> voices =
                    objectMapper.readValue(
                            response,
                            new TypeReference<
                                    Map<String, Map<String, Object>>
                                    >() {}
                    );

            List<VoiceResponse> result =
                    new ArrayList<>();

            for (
                    Map.Entry<
                            String,
                            Map<String, Object>
                            > entry : voices.entrySet()
            ) {

                String voiceId =
                        entry.getKey();

                Map<String, Object> config =
                        entry.getValue();

                String language =
                        getFrontendLanguage(
                                voiceId
                        );

                String name =
                        getString(
                                config,
                                "name"
                        );

                String quality =
                        getString(
                                config,
                                "quality"
                        );

                int numSpeakers =
                        getInteger(
                                config,
                                "num_speakers"
                        );

                String gender =
                        getGender(
                                voiceId
                        );

                result.add(
                        new VoiceResponse(
                                voiceId,
                                language,
                                name,
                                quality,
                                numSpeakers,
                                gender
                        )
                );
            }

            return result;

        } catch (TtsException exception) {

            throw exception;

        } catch (Exception exception) {

            throw new TtsException(
                    "Unable to get voices from Piper. "
                            +
                    "Please make sure Piper is running on port 5000.",
                    exception
            );
        }
    }

    private String getFrontendLanguage(
            String voiceId
    ) {

        if (
                voiceId.startsWith("en_US-")
        ) {
            return "en-US";
        }

        if (
                voiceId.startsWith("hi_IN-")
        ) {
            return "hi-IN";
        }

        if (
                voiceId.startsWith("es_ES-")
        ) {
            return "es-ES";
        }

        if (
                voiceId.startsWith("fr_FR-")
        ) {
            return "fr-FR";
        }

        if (
                voiceId.startsWith("de_DE-")
        ) {
            return "de-DE";
        }

        return "unknown";
    }

    private String getGender(
            String voiceId
    ) {

        if (
                voiceId.equals(
                        "en_US-hfc_female-medium"
                )
        ) {
            return "female";
        }

        if (
                voiceId.equals(
                        "en_US-lessac-medium"
                )
        ) {
            return "male";
        }

        if (
                voiceId.equals(
                        "hi_IN-priyamvada-medium"
                )
        ) {
            return "female";
        }

        if (
                voiceId.equals(
                        "hi_IN-rohan-medium"
                )
        ) {
            return "male";
        }

        return "unknown";
    }

    private String getString(
            Map<String, Object> map,
            String key
    ) {

        Object value =
                map.get(key);

        return value != null
                ? value.toString()
                : "";
    }

    private int getInteger(
            Map<String, Object> map,
            String key
    ) {

        Object value =
                map.get(key);

        if (
                value instanceof Number number
        ) {
            return number.intValue();
        }

        return 0;
    }
}
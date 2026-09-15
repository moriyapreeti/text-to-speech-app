package com.tts.app.service.provider;

import com.tts.app.dto.TtsRequest;
import com.tts.app.exception.TtsException;
import com.tts.app.service.voice.VoiceModelService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Component
public class PiperTtsProvider implements TtsProvider {

    private final RestClient restClient;

    private final VoiceModelService voiceModelService;

    public PiperTtsProvider(
            VoiceModelService voiceModelService
    ) {

        this.voiceModelService =
                voiceModelService;

        this.restClient =
                RestClient.builder()
                        .baseUrl(
                                "http://localhost:5000"
                        )
                        .build();
    }

    @Override
    public byte[] synthesize(
            TtsRequest request
    ) {

        try {

            String voiceModel =
                    voiceModelService.getVoiceModel(
                            request.getLanguage(),
                            request.getVoice()
                    );

            Map<String, Object> body =
                    new HashMap<>();

            body.put(
                    "text",
                    request.getText()
            );

            body.put(
                    "voice",
                    voiceModel
            );

            ResponseEntity<byte[]> response =
                    restClient
                            .post()
                            .uri("/synthesize")
                            .contentType(
                                    MediaType.APPLICATION_JSON
                            )
                            .body(body)
                            .retrieve()
                            .toEntity(byte[].class);

            if (
                    response.getBody() == null ||
                    response.getBody().length == 0
            ) {

                throw new TtsException(
                        "Piper returned empty audio."
                );
            }

            return response.getBody();

        } catch (TtsException exception) {

            throw exception;

        } catch (Exception exception) {

            throw new TtsException(
                    "Unable to generate speech using Piper TTS. "
                            +
                    "Please make sure Piper is running on port 5000.",
                    exception
            );
        }
    }
}
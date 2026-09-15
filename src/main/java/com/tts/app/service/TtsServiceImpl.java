package com.tts.app.service;

import com.tts.app.dto.PreviewRequest;
import com.tts.app.dto.TtsRequest;
import com.tts.app.exception.InvalidTtsRequestException;
import com.tts.app.service.provider.TtsProvider;
import org.springframework.stereotype.Service;

@Service
public class TtsServiceImpl implements TtsService {

    private final TtsProvider ttsProvider;


    public TtsServiceImpl(
            TtsProvider ttsProvider
    ) {
        this.ttsProvider = ttsProvider;
    }


    @Override
    public byte[] generateSpeech(
            TtsRequest request
    ) {

        if (
                request.getText() == null ||
                request.getText().trim().isEmpty()
        ) {

            throw new InvalidTtsRequestException(
                    "Text cannot be empty."
            );
        }


        if (
                request.getLanguage() == null ||
                request.getLanguage().isBlank()
        ) {

            throw new InvalidTtsRequestException(
                    "Language is required."
            );
        }


        if (
                request.getVoice() == null ||
                request.getVoice().isBlank()
        ) {

            throw new InvalidTtsRequestException(
                    "Voice is required."
            );
        }


        return ttsProvider.synthesize(request);
    }


    @Override
    public byte[] generatePreview(
            PreviewRequest request
    ) {

        if (
                request.getLanguage() == null ||
                request.getLanguage().isBlank()
        ) {

            throw new InvalidTtsRequestException(
                    "Language is required."
            );
        }


        if (
                request.getVoice() == null ||
                request.getVoice().isBlank()
        ) {

            throw new InvalidTtsRequestException(
                    "Voice is required."
            );
        }


        String previewText =
                getPreviewText(
                        request.getLanguage()
                );


        TtsRequest ttsRequest =
                new TtsRequest(
                        previewText,
                        request.getLanguage(),
                        request.getVoice()
                );


        return ttsProvider.synthesize(
                ttsRequest
        );
    }


    private String getPreviewText(
            String language
    ) {

        return switch (language) {

            case "en-US" ->
                    "Hello, this is a voice preview.";

            case "hi-IN" ->
                    "नमस्ते, यह आवाज़ का परीक्षण है।";

            case "es-ES" ->
                    "Hola, esta es una prueba de voz.";

            case "fr-FR" ->
                    "Bonjour, ceci est un test de voix.";

            case "de-DE" ->
                    "Hallo, dies ist ein Sprachtest.";

            default ->
                    "Hello, this is a voice preview.";
        };
    }
}
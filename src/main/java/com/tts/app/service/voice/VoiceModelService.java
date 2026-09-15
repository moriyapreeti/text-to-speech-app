package com.tts.app.service.voice;

import com.tts.app.dto.VoiceResponse;
import com.tts.app.exception.InvalidTtsRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoiceModelService {

    private final PiperVoiceService piperVoiceService;

    public VoiceModelService(
            PiperVoiceService piperVoiceService
    ) {
        this.piperVoiceService = piperVoiceService;
    }

    public String getVoiceModel(
            String language,
            String voice
    ) {

        if (
                language == null ||
                language.isBlank()
        ) {
            throw new InvalidTtsRequestException(
                    "Language is required."
            );
        }

        if (
                voice == null ||
                voice.isBlank()
        ) {
            throw new InvalidTtsRequestException(
                    "Voice is required."
            );
        }

        List<VoiceResponse> availableVoices =
                piperVoiceService.getAvailableVoices();

        boolean voiceAvailable =
                availableVoices
                        .stream()
                        .anyMatch(item ->
                                item.getId().equals(voice)
                                        &&
                                item.getLanguage().equals(language)
                        );

        if (!voiceAvailable) {

            throw new InvalidTtsRequestException(
                    "Voice '" + voice +
                    "' is not available for language '" +
                    language + "'."
            );
        }

        return voice;
    }
}
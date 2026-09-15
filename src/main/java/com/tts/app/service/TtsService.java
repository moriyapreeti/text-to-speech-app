package com.tts.app.service;

import com.tts.app.dto.PreviewRequest;
import com.tts.app.dto.TtsRequest;

public interface TtsService {

    byte[] generateSpeech(
            TtsRequest request
    );

    byte[] generatePreview(
            PreviewRequest request
    );
}
package com.tts.app.service.provider;

import com.tts.app.dto.TtsRequest;

public interface TtsProvider {

    byte[] synthesize(TtsRequest request);

}


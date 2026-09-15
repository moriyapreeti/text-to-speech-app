package com.tts.app.controller;

import com.tts.app.dto.PreviewRequest;
import com.tts.app.dto.TtsRequest;
import com.tts.app.dto.VoiceResponse;
import com.tts.app.service.TtsService;
import com.tts.app.service.voice.PiperVoiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tts")
public class TtsController {

    private final TtsService ttsService;

    private final PiperVoiceService piperVoiceService;


    public TtsController(
            TtsService ttsService,
            PiperVoiceService piperVoiceService
    ) {

        this.ttsService =
                ttsService;

        this.piperVoiceService =
                piperVoiceService;
    }


    @PostMapping(
            value = "/generate",
            produces = "audio/wav"
    )
    public ResponseEntity<byte[]> generateSpeech(
            @Valid @RequestBody TtsRequest request
    ) {

        byte[] audio =
                ttsService.generateSpeech(
                        request
                );


        return ResponseEntity
                .ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"generated-speech.wav\""
                )
                .contentType(
                        MediaType.parseMediaType(
                                "audio/wav"
                        )
                )
                .body(audio);
    }


    @PostMapping(
            value = "/preview",
            produces = "audio/wav"
    )
    public ResponseEntity<byte[]> previewVoice(
            @Valid @RequestBody PreviewRequest request
    ) {

        byte[] audio =
                ttsService.generatePreview(
                        request
                );


        return ResponseEntity
                .ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"voice-preview.wav\""
                )
                .contentType(
                        MediaType.parseMediaType(
                                "audio/wav"
                        )
                )
                .body(audio);
    }


    @GetMapping("/voices")
    public ResponseEntity<List<VoiceResponse>> getVoices() {

        List<VoiceResponse> voices =
                piperVoiceService
                        .getAvailableVoices();


        return ResponseEntity.ok(
                voices
        );
    }
}
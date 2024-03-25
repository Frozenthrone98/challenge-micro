package com.mamani.mscustomeralternative.exception;

import java.time.LocalDateTime;
public record CustomErrorResponse(
        LocalDateTime dateTime,
        String message,
        String path
) {
}

package utils;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
class RequestObjectParser {
    private final HttpServletRequest req;

    /**
     * Reconstructs object from request into supplied blank, returning reference to it.
     */
    <T> T reconstruct(T blank) {
        return blank;
    }
}

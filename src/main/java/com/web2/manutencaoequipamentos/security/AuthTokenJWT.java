package com.web2.manutencaoequipamentos.security;

import java.time.Instant;

public record AuthTokenJWT(String token, Instant expiresAt) {
}


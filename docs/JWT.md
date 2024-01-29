# Configuring JWT (Json Web Token)

To setup JWT, we need to create a SecurityConfig.java file and EnableWebSecurity

We can authorize requests based on requestMatchers, which are Strings that allows endpoints to be accessed based on if the request was authenticated or not. Later, we can add specific roles and require requests to be authenticated for admin only endpoints.

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .decoder(jwtDecoder())
                )
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}
```

We can declare jwtDecoder and jwtEncoder functions and inject them with the @Beans annotation

```java
    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(keys.getPublicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(keys.getPublicKey()).privateKey(keys.getPrivateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }
```

## Creating public and private keys

We will be using asymmetric keys in this project to encrypt our JWT

Asymmetric Keys: Different keys are used for encryption (private key) and decryption (public key). The public key is stored as a setting in MobileTogether Server so that the JWT can be verified.

1. Create a new folder in the resources folder

```
src
│
├───main
│   │
│   └───resources
│       │   application.properties
│       ├───certs
│       │   │   keypair.pem
│       │   │   private.pem
│       │   │   public.pem
│       │
│       ├───static
│       └───templates
```

2. Generate RSA Key

```bash
openssl genrsa -out keypair.pem 2048
```

3. Generate Public Key

```bash
openssl rsa -in keypair.pem -pubout public.pem
```

4. Generating Private Key
```bash
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
```

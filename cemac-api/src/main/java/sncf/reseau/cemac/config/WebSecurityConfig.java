package sncf.reseau.cemac.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.sncf.reseau.jraf.security.web.reactive.oidc.WebSecurityConfigOidc;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigOidc {

   @Bean
   public SecurityWebFilterChain appSpringSecurityFilterChain(ServerHttpSecurity http) {
      
      return
            // [JRAF] Config OIDC
            super.jrafSpringSecurityBaseConfig(http)
               .authorizeExchange(exchange -> exchange
                  // [JRAF] URIs OIDC/OAuth2 -> permitAll()
                  .pathMatchers(WebSecurityConfigOidc.JRAF_PATH_MATCHERS).permitAll()
                  .pathMatchers("/actuator/**").permitAll()
                  // URIs de l'application à sécuriser
                  .anyExchange().authenticated())
               .build();
   }
}

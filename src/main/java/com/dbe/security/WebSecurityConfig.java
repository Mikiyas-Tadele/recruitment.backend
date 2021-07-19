package com.dbe.security;

import com.dbe.security.jwt.JwtAuthEntryPoint;
import com.dbe.security.jwt.JwtAuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    private static String SALT = "0x305D6CC6A042555F53922ECA230EE23C407D7887C8D2A0F8AFD4D3350B261A88557B06BD2A4D1013D4D70C335C3A011AD7131ADBA458475EBC7316CB6E20542D77A675A8072511F26D65B3F0C66D9C75D2B85DF70B3FE3FC33A700B63744A4B483DEBBB6C55913B202286D3FA557984FD7A1F9B6C25C2138C378745F255ABB29C1FF6CE7B1E6B079A5ABF5E3D1A356DC0FB0DF964BEDF127BD83F24CE5B38CA76D447EA6EAB19EF1F0801017673E661F229750747B24DED30D45292EF36393F21E4D768D25B0F25E12EEA5CADDDABF7E1CD32A4D63172C4866AB99A5876FFE8DEC1EF91AC7D8FA71A4F1628D7F13F53385AA111F39458E06202E2779164DD7A3F018DEBEE1A5B743D9B8A971B59FC853B5661FDC5DDE53A68FF2F999B1805A571ABF9D67343752DFF3CD46819C1D508065D4C4B02DFA4511C4372EEEAB1D92595D5156F602601E7E1440E88F98D20972A27833DDBBAA2A1F798BA2FC086333D53BEC234BC65A7E04B5469CED12B579F22EBC4C33EAB59DB326367C76BE2175B00C1254ABAEBEAB47FA2E27750D3D0DC311B0CE4C1700EB864F70248FDE074694D0ECE0FF61CE436F100438BB8D27A5E828B8640D5229980B27CD5B3E991B69F2E793A4215C0E61436C5E3B13C730F927CA3B6626A23941A753D5D7947205D6F49B46D3FA83848C1A5621A0F3ED8543F7D434735DB5434FAE51FCCD8736CDD167";

    private static final String[] PUBLIC_MATCHERS ={
            "/api/auth/login",
             "/api/auth/register-user"
    };

    @Autowired
    private JwtAuthEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    public PasswordEncoder passwordEncoder() {
//
//        return  new BCryptPasswordEncoder(50,new SecureRandom(SALT.getBytes()));
//    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().
                authorizeRequests()
     //           .antMatchers("/api/auth/**").permitAll()
                .antMatchers(PUBLIC_MATCHERS).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
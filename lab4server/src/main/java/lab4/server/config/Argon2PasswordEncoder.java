package lab4.server.config;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.password4j.Password;

public class Argon2PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return Password.hash(rawPassword.toString()).addRandomSalt().withArgon2().getResult();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return Password.check(rawPassword.toString(), encodedPassword).withArgon2();
    }
}

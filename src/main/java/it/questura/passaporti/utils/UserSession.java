package it.questura.passaporti.utils;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UserSession {
    private String fiscalCode;

    public void resetSession() {
        fiscalCode = "";
    }
}

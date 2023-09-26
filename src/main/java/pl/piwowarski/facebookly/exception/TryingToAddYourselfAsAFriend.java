package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TryingToAddYourselfAsAFriend extends RuntimeException {
    public static final String MESSAGE = "Brak możliwości dodania samego siebie do znajomych";
    private String message;
}

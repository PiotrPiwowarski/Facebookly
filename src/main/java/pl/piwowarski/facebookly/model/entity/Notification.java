package pl.piwowarski.facebookly.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.piwowarski.facebookly.model.enums.NotificationType;

import static jakarta.persistence.GenerationType.IDENTITY;

@Table(name = "notifications")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy= IDENTITY)
    private Long id;
    private NotificationType notificationType;
    private String description;
}

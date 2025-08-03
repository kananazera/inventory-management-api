package az.inventory.inventorymanagementapi.entity;

import az.inventory.inventorymanagementapi.enums.ContactType;
import az.inventory.inventorymanagementapi.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String phone;

    private String address;

    private Gender gender;

    private LocalDate birthDate;

    private Boolean active = Boolean.TRUE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContactType contactType;

    private Long tin;

    @OneToMany(mappedBy = "supplier")
    private List<Contract> contracts;
}
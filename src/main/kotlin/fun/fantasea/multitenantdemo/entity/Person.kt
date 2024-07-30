package `fun`.fantasea.multitenantdemo.entity

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Entity
@Table(name = "t_person")
data class Person(
    val name: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null
}


@Repository
interface PersonRepository : JpaRepository<Person, String>

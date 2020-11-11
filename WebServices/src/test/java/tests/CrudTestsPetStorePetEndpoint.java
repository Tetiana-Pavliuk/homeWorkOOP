package tests;

import endpoints.PetStorePetEndpoint;
import io.restassured.response.Response;
import models.Pet;
import net.thucydides.core.annotations.Title;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CrudTestsPetStorePetEndpoint {

    public static final endpoints.PetStorePetEndpoint PET_STORE_PET_ENDPOINT = new PetStorePetEndpoint();

    @BeforeEach
    public void cleanUp() {

        PET_STORE_PET_ENDPOINT
                .getPetByStatus("available")
                .body()
                .jsonPath().getList("findAll {item -> item.name == 'Barsik'}", Pet.class)
                .stream()
                .forEach(pet -> PET_STORE_PET_ENDPOINT.deleteById(pet.getId()));
        System.out.println();
    }

    @Test
    @Title("verification of get request")
    public void createPet() {
        //Given
        Pet barsik = Pet.createBarsik();

        //When
        Response petResponse = PET_STORE_PET_ENDPOINT.createPet(barsik);

        //Then
        long createdPetId = petResponse.body().as(Pet.class).getId();
        Pet createdPetFromService = PET_STORE_PET_ENDPOINT.getPetById(String.valueOf(createdPetId)).as(Pet.class);

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(createdPetFromService.getName()).as("Name").isEqualTo(barsik.getName());
        assertions.assertThat(createdPetFromService.getStatus()).as("Name").isEqualTo(barsik.getStatus());
        assertions.assertAll();
    }

    @Test
    public void readPet() {
        //Given
        Pet barsik = Pet.createBarsik();
        Response petResponse = PET_STORE_PET_ENDPOINT.createPet(barsik);
        long createdPetId = petResponse.body().as(Pet.class).getId();

        //When
        Pet createdPetFromService = PET_STORE_PET_ENDPOINT.getPetById(String.valueOf(createdPetId)).as(Pet.class);

        //Then
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(createdPetFromService.getName()).as("Name").isEqualTo(barsik.getName());
        assertions.assertThat(createdPetFromService.getStatus()).as("Name").isEqualTo(barsik.getStatus());
        assertions.assertAll();
    }

    @Test
    public void updatePet() {
        //Given
        Pet barsik = Pet.createBarsik();
        Response petResponse = PET_STORE_PET_ENDPOINT.createPet(barsik);
        long createdPetId = petResponse.body().as(Pet.class).getId();
        barsik.setId(createdPetId);
        barsik.setStatus("sold");

        //When
        PET_STORE_PET_ENDPOINT.updatePet(barsik).body().as(Pet.class);

        //Then
        Pet createdPetFromService = PET_STORE_PET_ENDPOINT.getPetById(String.valueOf(createdPetId)).as(Pet.class);
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(createdPetFromService.getName()).as("Name").isEqualTo(barsik.getName());
        assertions.assertThat(createdPetFromService.getStatus()).as("Status").isEqualTo(barsik.getStatus());
        assertions.assertAll();
    }

    @Test
    public void deletePet() {
        //Given
        Pet barsik = Pet.createBarsik();
        Response petResponse = PET_STORE_PET_ENDPOINT.createPet(barsik);
        long createdPetId = petResponse.body().as(Pet.class).getId();
        Pet createdPetFromService = PET_STORE_PET_ENDPOINT.getPetById(String.valueOf(createdPetId)).body().as(Pet.class);

        //When
        PET_STORE_PET_ENDPOINT.deleteById(createdPetFromService.getId());

        //Then
        Response petById = PET_STORE_PET_ENDPOINT.getPetById(String.valueOf(createdPetFromService.getId()));
        Assertions.assertThat(petById.getStatusCode()).isEqualTo(404);
    }
}

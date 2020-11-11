package serenity.bdd.steps.serenity;

import org.assertj.core.api.Assertions;
import serenity.bdd.pages.DictionaryPage;
import net.thucydides.core.annotations.Step;
import com.google.common.collect.ImmutableList;
import endpoints.PetStorePetEndpoint;
import models.Category;
import models.Pet;
import org.assertj.core.api.SoftAssertions;
import serenity.bdd.utils.EnvironmentPropertyLoader;
import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;

public class EndUserSteps {
    private static Pet barsik;
    private long createdPetId;
    private Pet createdPetFromService;
    public static final endpoints.PetStorePetEndpoint PET_STORE_PET_ENDPOINT = new PetStorePetEndpoint();
    private Response response;

    DictionaryPage dictionaryPage;

    @Step
    public void enters(String keyword) {
        dictionaryPage.enter_keywords(keyword);
    }

    @Step
    public void starts_search() {
        dictionaryPage.lookup_terms();
    }

    @Step
    public void should_see_definition(String definition) {
        assertThat(dictionaryPage.getDefinitions(), hasItem(containsString(definition)));
    }

    @Step
    public void is_the_home_page() {
        dictionaryPage.open();
    }

    @Step
    public void looks_for(String term) {
        enters(term);
        starts_search();
    }

    @Step
    public void property_check() {
        EnvironmentPropertyLoader instance = EnvironmentPropertyLoader.getInstance();
        String propertyValue = instance.getProperty("my.property");
        System.out.println(propertyValue);
    }

    @Step
    public void createBarsik() {
        Category category = new Category();
        category.setId(123010);
        category.setName("Cats");

        barsik = new Pet();
        barsik.setName("Barsik");
        barsik.setPhotoUrls(ImmutableList.of("SomeURL"));
        barsik.setCategory(category);
        barsik.setStatus("available");
    }

    @Step
    public void createPet() {
        response = PET_STORE_PET_ENDPOINT.createPet(barsik);
    }

    @Step
    public void verifyPet() {
        long createdPetId = response.body().as(Pet.class).getId();

        Pet createdPetFromService = PET_STORE_PET_ENDPOINT.getPetById(String.valueOf(createdPetId)).as(Pet.class);

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(createdPetFromService.getName()).as("Name").isEqualTo(barsik.getName());
        assertions.assertThat(createdPetFromService.getStatus()).as("Name").isEqualTo(barsik.getStatus());
        assertions.assertAll();
    }

    @Step
    public void createPetForUpdate() {
        Pet barsik = Pet.createBarsik();
        Response petResponse = PET_STORE_PET_ENDPOINT.createPet(barsik);
        createdPetId = petResponse.body().as(Pet.class).getId();
        barsik.setId(createdPetId);
        barsik.setStatus("sold");
    }

    @Step
    public void updatePet() {
        PET_STORE_PET_ENDPOINT.updatePet(barsik).body().as(Pet.class);
    }

    @Step
    public void checkUpdatePet() {
        Pet createdPetFromService = PET_STORE_PET_ENDPOINT.getPetById(String.valueOf(createdPetId)).as(Pet.class);
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(createdPetFromService.getName()).as("Name").isEqualTo(barsik.getName());
        assertions.assertThat(createdPetFromService.getStatus()).as("Status").isEqualTo(barsik.getStatus());
        assertions.assertAll();
    }

    @Step
    public void createPetForDelete() {
        Pet barsik = Pet.createBarsik();
        Response petResponse = PET_STORE_PET_ENDPOINT.createPet(barsik);
        long createdPetId = petResponse.body().as(Pet.class).getId();
        createdPetFromService = PET_STORE_PET_ENDPOINT.getPetById(String.valueOf(createdPetId)).body().as(Pet.class);
    }

    @Step
    public void deletePet() {
        PET_STORE_PET_ENDPOINT.deleteById(createdPetFromService.getId());
    }

    @Step
    public void checkDeletePet() {
        Response petById = PET_STORE_PET_ENDPOINT.getPetById(String.valueOf(createdPetFromService.getId()));
        Assertions.assertThat(petById.getStatusCode()).isEqualTo(404);
    }
}
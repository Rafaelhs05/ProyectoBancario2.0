package app.application.service.imp;

import java.time.LocalDate;
import java.util.List;
import app.domain.model.StatusCustomer;
import org.bouncycastle.crypto.RuntimeCryptoException;
import org.springframework.stereotype.Service;
import app.application.mapper.MapperCustomer;
import app.application.service.ServiceCustomer;
import app.domain.model.City;
import app.domain.model.Country;
import app.domain.model.Customer;
import app.domain.repository.RepositoryCity;
import app.domain.repository.RepositoryCountry;
import app.domain.repository.RepositoryCustomer;
import app.web.dto.RequestCustomer;
import app.web.dto.ResponseCity;
import app.web.dto.ResponseCustomer;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceCustomerImp implements ServiceCustomer {

    private final RepositoryCustomer repositoryCustomer;
    private final MapperCustomer mapperCustomer;
    private final RepositoryCity repositoryCity;
    private final RepositoryCountry repositoryCountry;

    @Override
    public ResponseCustomer nuevoCustomer(RequestCustomer requestCustomer) {

        if (repositoryCustomer.existsByDocumentNumber(requestCustomer.getDocumentNumber())) {
            throw new RuntimeException("El número de documento ya existe.");
        }

        if (repositoryCustomer.existsByEmail(requestCustomer.getEmail())) {
            throw new RuntimeException("El correo electrónico ya existe.");
        }

        if (repositoryCustomer.existsByPhoneNumber(requestCustomer.getPhoneNumber())) {
            throw new RuntimeException("El número de teléfono ya existe.");
        }

        if (requestCustomer.getAge() < 18) {
            throw new RuntimeException("El cliente debe ser mayor de edad.");

        }

        City city = repositoryCity.findById(requestCustomer.getCityId())
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada con ID: " + requestCustomer.getCityId()));

        Customer nuevoCliente = mapperCustomer.toEntity(requestCustomer, city);
        nuevoCliente.setDateCreation(LocalDate.now());
        nuevoCliente.setStatus(StatusCustomer.ACTIVO);
        Customer registrarCliente = repositoryCustomer.save(nuevoCliente);
        ResponseCity cityDto = mapperCustomer.toDtoCity(city, city.getCountry());
        return mapperCustomer.toDto(registrarCliente, cityDto);
    }

    @Override
    public ResponseCustomer actualizarCustomer(RequestCustomer requestCustomer) {

        Customer cliente = repositoryCustomer.findByDocumentNumber(requestCustomer.getDocumentNumber()).orElseThrow(
                () -> new RuntimeException("Cliente no encontado"));

        if (!cliente.getDocumentNumber().equals(requestCustomer.getDocumentNumber()) &&
                repositoryCustomer.existsByDocumentNumber(requestCustomer.getDocumentNumber())) {
            throw new RuntimeException("El documento ya existe en otro cliente");
        }

        if (!cliente.getPhoneNumber().equals(requestCustomer.getPhoneNumber()) &&
                repositoryCustomer.existsByPhoneNumber(requestCustomer.getPhoneNumber())) {

            throw new RuntimeException("El numero ya existe en otro cliente");
        }

        if (!cliente.getEmail().equals(requestCustomer.getEmail()) &&
                repositoryCustomer.existsByEmail(requestCustomer.getEmail())) {
            throw new RuntimeException("El correo ya existe en otro cliente");
        }

        City city = repositoryCity.findById(requestCustomer.getCityId()).orElseThrow(
                () -> new RuntimeException("Ciudad no encontrada"));

        Country country = repositoryCountry.findById(city.getCountry().getIdCountry()).orElseThrow(
                () -> new RuntimeException("Pais no encontrado"));

        cliente.setName(requestCustomer.getName());
        cliente.setLastName(requestCustomer.getLastName());
        cliente.setPhoneNumber(requestCustomer.getPhoneNumber());
        cliente.setDocumentType(requestCustomer.getDocumentType());
        cliente.setDocumentNumber(requestCustomer.getDocumentNumber());
        cliente.setEmail(requestCustomer.getEmail());
        cliente.setAddress(requestCustomer.getAddress());
        cliente.setBirthDate(requestCustomer.getBirthDate());
        cliente.setAge(requestCustomer.getAge());
        cliente.setCity(city);
        Customer updateCustomer = repositoryCustomer.save(cliente);
        ResponseCity dtoCity = mapperCustomer.toDtoCity(city, country);
        return mapperCustomer.toDto(updateCustomer, dtoCity);
    }

    @Override
    public ResponseCustomer eliminarCustomer(Long idCustomer) {

        Customer buscar = repositoryCustomer.findById(idCustomer).orElseThrow(
                () -> new RuntimeException("No se encontro al cliente"));

        City city = repositoryCity.findById(buscar.getCity().getIdCity()).orElseThrow(
                () -> new RuntimeException("Ciudad no encontrada"));

        Country country = repositoryCountry.findById(city.getCountry().getIdCountry()).orElseThrow(
                () -> new RuntimeException("Pais no encontrado"));

        ResponseCity dtocity = mapperCustomer.toDtoCity(city, country);

        repositoryCustomer.deleteById(buscar.getIdCustomer());

        return mapperCustomer.toDto(buscar, dtocity);
    }

    @Override
    public ResponseCustomer obtenerCustomerPorId(Long idCustomer) {

        Customer cliente = repositoryCustomer.findById(idCustomer).orElseThrow(
                () -> new RuntimeException("Cliente no encontrado"));

        City city = repositoryCity.findById(cliente.getCity().getIdCity()).orElseThrow(
                () -> new RuntimeException("Ciudad no encontrado"));

        Country country = repositoryCountry.findById(city.getCountry().getIdCountry()).orElseThrow(
                () -> new RuntimeException("Pais no encontrado"));

        ResponseCity dtocCity = mapperCustomer.toDtoCity(city, country);

        return mapperCustomer.toDto(cliente, dtocCity);

    }

    @Override
    public List<ResponseCustomer> obtenerTodosLosCustomers() {

        return repositoryCustomer.findAll().stream().map(cliente -> {

            City city = repositoryCity.findById(cliente.getCity().getIdCity()).orElseThrow(
                    () -> new RuntimeException("Ciudad no encontrado"));

            Country country = repositoryCountry.findById(city.getCountry().getIdCountry()).orElseThrow(
                    () -> new RuntimeException("Pais no encontrado"));

            ResponseCity dtocCity = mapperCustomer.toDtoCity(city, country);

            return mapperCustomer.toDto(cliente, dtocCity);

        }).toList();

    }

    @Override
    public List<ResponseCity> obtenerTodasLasCiudades() {

        return repositoryCity.findAll().stream().map(city -> {
            Country country = repositoryCountry.findById(city.getCountry().getIdCountry()).orElseThrow(
                    () -> new RuntimeException("No existe el pais"));
            return mapperCustomer.toDtoCity(city, country);
        }

        ).toList();
    }

    @Override
    public ResponseCustomer obtenerCustomerPorDni(String dni) {

        return repositoryCustomer.findByDocumentNumber(dni).map(cliente -> {

            City city = repositoryCity.findById(cliente.getCity().getIdCity()).orElseThrow(
                    () -> new RuntimeException("Ciudad no encontrada"));

            Country country = repositoryCountry.findById(city.getCountry().getIdCountry()).orElseThrow(
                    () -> new RuntimeException("Pais no encontrado"));

            ResponseCity dtoCity = mapperCustomer.toDtoCity(city, country);

            return mapperCustomer.toDto(cliente, dtoCity);

        }).orElseThrow(() -> new RuntimeCryptoException("Cliente no encontrado"));
    }

}

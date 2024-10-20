package softuni.exam.areImported;
//TestAstronomerServiceAreImportedFalse

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import softuni.exam.repository.AstronomerRepository;
import softuni.exam.service.impl.AstronomerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TestAstronomerServiceAreImportedFalse {


    @InjectMocks
    private AstronomerServiceImpl astronomerService;
    @Mock
    private AstronomerRepository mockAstronomerRepository;

    @Test
    void areImportedShouldReturnFalse() {
        Mockito.when(mockAstronomerRepository.count()).thenReturn(0L);
        Assertions.assertFalse(astronomerService.areImported());
    }
}

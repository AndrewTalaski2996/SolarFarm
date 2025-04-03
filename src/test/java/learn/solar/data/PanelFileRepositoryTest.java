package learn.solar.data;

import learn.solar.models.Material;
import learn.solar.models.Panel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PanelFileRepositoryTest {

    private static final String TEST_PATH = "./data/panel-test.csv";
    private static final String SEED_PATH = "./data/panel-seed.csv";

    private final PanelFileRepository repository = new PanelFileRepository(TEST_PATH);

    @BeforeEach
    void setup() throws DataException, IOException {
        Path seedPath = Paths.get(SEED_PATH);
        Path testPath = Paths.get(TEST_PATH);

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    /*
    id,section,row,column,installationYear,material,tracking
1,"Main",1,1,2018,CIGS,true
2,"Upper Hill",1,2,2014,AmorSi,true
3,"Lower Hill",2,3,2017,CdTe,false
     */

    @Test
    void findAll() throws DataException {
        List<Panel> actual = repository.findAll();
        assertEquals(3, actual.size());

        Panel panel = actual.get(0);
        assertEquals(1, panel.getId());
        assertEquals("Main", panel.getSection());
        assertEquals(1,panel.getRow());
        assertEquals(1,panel.getColumn());
        assertEquals(2018,panel.getInstallationYear());
        assertEquals(Material.CIGS, panel.getMaterial());
        assertTrue(panel.isTracking());
    }

    @Test
    void findBySection() throws DataException {
        List<Panel> panels = repository.findBySection("Main");
        assertEquals(1,panels.size());
    }

    @Test
    void add() throws DataException {
        Panel panel = new Panel();
        panel.setSection("Upper Hill");
        panel.setRow(2);
        panel.setColumn(1);
        panel.setInstallationYear(2016);
        panel.setMaterial(Material.CdTe);
        panel.setTracking(true);

        Panel actual = repository.add(panel);

        assertNotNull(actual);
        assertEquals(4, actual.getId());
    }

    @Test
    void update() throws DataException {
        Panel panel = repository.findAll().get(0);
        panel.setInstallationYear(2020);
        boolean result = repository.update(panel);
        assertTrue(result);
        assertNotNull(panel);
        //add tests
    }

    @Test
    void shouldNotUpdate() throws DataException {
        Panel panel = new Panel(999,"Main",1,3,2013,Material.CIGS,true);
        boolean result = repository.update(panel);
        assertFalse(result);
    }

    @Test
    void deleteById() throws DataException {
        boolean result = repository.deleteById(1);
        assertEquals(2, repository.findAll().size());
        assertTrue(result);
    }

    @Test
    void doNotDeleteUnknownId() throws DataException {
        boolean result = repository.deleteById(9999);
        assertFalse(result);
    }
}
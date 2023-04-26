package service;

import model.Model;
import repository.ModelRepository;
import repository.ModelUpdateRepository;

import java.io.IOException;
import java.util.List;

public class ModelService {
    private ModelRepository modelRepository;
    private ModelUpdateRepository modelUpdateRepository;
    public ModelService() {
        modelRepository = new ModelRepository();
        modelUpdateRepository = new ModelUpdateRepository();
    }
    public List<Model> getAllModel() throws IOException {
        return modelRepository.getAll();
    }
    public List<Model> getAllModelUpdate() throws IOException {
        return modelUpdateRepository.getAll();
    }
    public int checkIdModel(int id) throws IOException {
        return modelRepository.checkID(id);
    }
    public int checkNameModel(String name) throws IOException {
        return modelRepository.checkName(name);
    }
    public void deleteModelById(int id) throws IOException {
        modelRepository.deleteById(id);
    }
    public void deleteModelUpdateById(int id) throws IOException {
        modelUpdateRepository.deleteById(id);
    }

}

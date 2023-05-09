package service;

import model.Model;
import model.Rental;
import model.User;
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
    public Model searchId(int idModel) throws IOException {
        List<Model> allModel = getAllModel();
        for (int i = 0; i < allModel.size(); i++) {
            if(allModel.get(i).getIdModel() == idModel) {
                return allModel.get(i);
            }
        }
        return null;
    }


}

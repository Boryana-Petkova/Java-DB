package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PostService;
import softuni.exam.instagraphlite.util.ValidationUtil;
import softuni.exam.instagraphlite.util.XmlParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PostServiceImpl implements PostService {

    public static final String POST_PATH = "src/main/resources/files/posts.xml";
    private final PostRepository postRepository;
    private final Gson gson;
    private  final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    private  final XmlParser xmlParser;

    public PostServiceImpl(PostRepository postRepository, Gson gson, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.postRepository = postRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = new ModelMapper();
        this.xmlParser = xmlParser;
    }


    @Override
    public boolean areImported() {
        return this.postRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(POST_PATH));
    }

    @Override
    public String importPosts() throws IOException {
        return null;
    }
}

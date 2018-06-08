package ru.voprostion.app.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.voprostion.app.domain.model.Question;
import ru.voprostion.app.domain.model.Role;
import ru.voprostion.app.domain.model.Tag;
import ru.voprostion.app.domain.model.User;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Set;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class TagQuestionRepositoryTest {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    User user1;
    User user2;

    Role role1;

    Question question1;
    Question question2;

    Tag tag1;
    Tag tag2;
    Tag tag3;
    Tag tag4;

    @Before
    public void setUp() throws Exception {
        questionRepository.deleteAll();
        tagRepository.deleteAll();

        role1 = new Role();

        role1.setName("role_1");

        roleRepository.save(role1);

        user1 = new User();
        user2 = new User();

        user1.setName("user1");
        user1.setPasswordHash("password1");

        user2.setName("user2");
        user2.setPasswordHash("password1");

        user1.addRole(role1);
        user1.addRole(role1);

        userRepository.save(user1);
        userRepository.save(user2);


        tag1 = new Tag();
        tag2 = new Tag();
        tag3 = new Tag();
        tag4 = new Tag();

        tag1.setTagName("tag1");
        tag2.setTagName("tag2");
        tag3.setTagName("tag3");
        tag4.setTagName("tag4");

        tagRepository.save(tag1);
        tagRepository.save(tag2);
        tagRepository.save(tag3);
        tagRepository.save(tag4);

        question1 = new Question();
        question2 = new Question();

        question1.setQuestionTitle("title_first");
        question2.setQuestionTitle("title_second");
    }


    @Test
    public void save_one_question_with_one_tag_saves() {
        final int sizeBefore = questionRepository.findAll().size();
        question1.addTag(tag1);
        questionRepository.save(question1);
        final int sizeAfter = questionRepository.findAll().size();

        Assert.assertEquals(sizeBefore + 1, sizeAfter);
    }

    @Test
    public void save_one_question_with_one_tag_saves_correctly() {
        question1.addTag(tag1);
        questionRepository.save(question1);
        final Question questionFromDb = questionRepository.findAll().get(0);
        final Set<Tag> tags = questionFromDb.getTags();
        final Tag tagFromDb = new ArrayList<>(tags).get(0);

        Assert.assertEquals(tag1.getTagName(), tagFromDb.getTagName());
    }

    @Test
    public void save_one_question_with_many_tags_correctly() {
        question1.addTag(tag1);
        question1.addTag(tag2);
        final Question saved = questionRepository.save(question1);
        final Question questionFromDb = questionRepository.findById(saved.getId()).get();
        final Set<Tag> tagsFromDb = questionFromDb.getTags();

        Assert.assertTrue(tagsFromDb.contains(tag1) && tagsFromDb.contains(tag2));
    }

    @Test
    public void changing_tags_changes_in_db() {
        question1.addTag(tag1);
        question1.addTag(tag2);
        questionRepository.save(question1);

        question1.getTags().clear();
        question1.addTag(tag3);
        question1.addTag(tag4);

        final Long savedQuestionId = questionRepository.save(question1).getId();
        final Question questionFromDb = questionRepository.findById(savedQuestionId).get();
        final Set<Tag> tagsFromDb = questionFromDb.getTags();

        final boolean correctSize = tagsFromDb.size() == 2;
        final boolean noOld = !tagsFromDb.contains(tag1) && !tagsFromDb.contains(tag2);
        final boolean newPresents = tagsFromDb.contains(tag3) && tagsFromDb.contains(tag4);

        Assert.assertTrue(correctSize && noOld && newPresents);
    }

    @Test
    public void deleting_tag_from_question_does_not_delete_it_from_db() {
        question1.addTag(tag1);
        question1.addTag(tag2);

        question2.addTag(tag1);
        question2.addTag(tag3);

        questionRepository.save(question1);
        questionRepository.save(question2);

        question1.removeTag(tag1);
        final Long savedId1 = questionRepository.save(question1).getId();
        final Question fromDb1 = questionRepository.findById(savedId1).get();
        final Set<Tag> fromDb1Tags = fromDb1.getTags();

        final int sizeAfterRemovingTag = questionRepository.findAll().size();

        Assert.assertTrue(sizeAfterRemovingTag == 2 &&
                fromDb1Tags.contains(tag2) &&
                !fromDb1Tags.contains(tag1));
    }
}

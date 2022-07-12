package music.service.impl;

import music.domain.Comment;
import music.mapper.CommentMapper;
import music.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 评论service实现类
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    @Transactional
    public boolean insert(Comment comment) {
        return commentMapper.insert(comment) > 0;
    }

    @Override
    @Transactional
    public boolean update(Comment comment) {
        return commentMapper.update(comment) > 0;
    }

    @Override
    @Transactional
    public boolean delete(Integer id) {
        return commentMapper.delete(id) > 0;
    }

    @Override
    @Transactional
    public Comment selectById(Integer id) {
        return commentMapper.selectById(id);
    }

    @Override
    @Transactional
    public List<Comment> selectAllComment() {
        return commentMapper.selectAllComment();
    }

    @Override
    @Transactional
    public List<Comment> selectBySongId(Integer songId) {
        return commentMapper.selectBySongId(songId);
    }

    @Override
    @Transactional
    public List<Comment> selectBySongListId(Integer songListId) {
        return commentMapper.selectBySongListId(songListId);
    }
}

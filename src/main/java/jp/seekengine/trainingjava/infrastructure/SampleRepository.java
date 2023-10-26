package jp.seekengine.trainingjava.infrastructure;

import jp.seekengine.trainingjava.infrastructure.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SampleRepository extends JpaRepository<MessageEntity, Integer> {
    List<MessageEntity> findByMessageContaining(String message);
}

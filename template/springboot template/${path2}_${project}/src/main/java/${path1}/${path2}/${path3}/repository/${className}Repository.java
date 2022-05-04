package ${pPackage}.repository;

import ${pPackage}.entity.${className};
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ${table.tableComment} dao层
 * author: ${author}
 * data: ${.now?date}
 */
public interface ${className}Repository extends JpaRepository<${className},Long> {
}

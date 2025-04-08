package com.altruist.api.repository

import com.altruist.api.model.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {
}

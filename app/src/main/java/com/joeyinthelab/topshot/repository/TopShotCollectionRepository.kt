package com.joeyinthelab.topshot.repository

import kotlinx.coroutines.flow.Flow

interface TopShotCollectionRepository {
    /**
     * Stream for Top Shot collection
     */
    val collection: Flow<List<String>>
}

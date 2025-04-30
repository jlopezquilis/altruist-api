package com.altruist.api.controller

import com.altruist.api.dto.request.CreateSimplifiedRequestRequest
import com.altruist.api.model.request.Request
import com.altruist.api.model.request.RequestId
import com.altruist.api.service.RequestService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/requests")
@Tag(name = "Solicitudes", description = "Operaciones relacionadas con las solicitudes de donaciones")
@CrossOrigin(origins = ["*"])
class RequestController(
    private val requestService: RequestService
) {

    @GetMapping("/{idUser}/{idPost}")
    @Operation(summary = "Obtener una solicitud por ID", description = "Obtiene una solicitud de donación por su ID")
    fun getRequestByUserAndPost(
        @PathVariable idUser: Long,
        @PathVariable idPost: Long
    ): ResponseEntity<Request> {
        val request = requestService.getRequestByUserAndPost(idUser, idPost)
        return request.map { ResponseEntity.ok(it) }
            .orElseGet { ResponseEntity.notFound().build() }
    }

    @PostMapping("/createRequest")
    fun createRequest(@RequestBody dto: CreateSimplifiedRequestRequest): ResponseEntity<Request> {
        val created = requestService.createRequest(dto)
        return ResponseEntity.ok(created)
    }

    @GetMapping("/user/{idUser}")
    @Operation(summary = "Obtener solicitudes por ID de usuario", description = "Obtiene las solicitudes de donación de un usuario por su ID")
    fun getRequestsByUser(@PathVariable idUser: Long): ResponseEntity<List<Request>> {
        val requests = requestService.getRequestsByUser(idUser)
        return ResponseEntity.ok(requests)
    }

    @GetMapping("/post/{idPost}")
    @Operation(summary = "Obtener solicitudes por ID de publicación", description = "Obtiene las solicitudes de donación de una publicación por su ID")
    fun getRequestsByPost(@PathVariable idPost: Long): ResponseEntity<List<Request>> {
        val requests = requestService.getRequestsByPost(idPost)
        return ResponseEntity.ok(requests)
    }

    @DeleteMapping("/{idUser}/{idPost}")
    @Operation(summary = "Eliminar una solicitud", description = "Elimina una solicitud de donación por su ID")
    fun deleteRequest(
        @PathVariable idUser: Long,
        @PathVariable idPost: Long
    ): ResponseEntity<Void> {
        val id = RequestId(idUser, idPost)
        return if (requestService.getRequestById(id).isPresent) {
            requestService.deleteRequest(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}

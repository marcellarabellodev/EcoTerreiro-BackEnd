package com.ecoterreiro.ecoterreiro_backend.handler;

import com.ecoterreiro.ecoterreiro_backend.exception.TerreiroNotFoundException;
import com.ecoterreiro.ecoterreiro_backend.exception.TerreiroValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice // Indica que esta classe é um "conselheiro" para todos os controladores, ou seja, centraliza o tratamento de erros em um só lugar.
@RestController // Garante que a resposta será formatada como JSON

public class RestExceptionHandler {

  // Esta anotação captura especificamente à TerreiroNotFoundException
  @ExceptionHandler(TerreiroNotFoundException.class)
    public ResponseEntity<Object> handleTerreiroNotFoundException(TerreiroNotFoundException ex, WebRequest request) {
    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", new Date()); // Data e hora do erro
    body.put("status", HttpStatus.NOT_FOUND.value());
    body.put("error", "Recurso não encontrado"); // Pode manter este como genérico ou usar a mensagem da exceção
    body.put("message", ex.getMessage()); // AQUI é onde a mensagem personalizada entra
    body.put("path", request.getDescription(false).substring(4)); // Adiciona o caminho da requisição./ O métdo substring(4) é usado para "cortar" os primeiros 4 caracteres da string retornada por getDescription(false).

    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  // Métdo para tratar a exceção TerreiroValidationException
  @ExceptionHandler(TerreiroValidationException.class)
  public ResponseEntity<Object> handleTerreiroValidationException(TerreiroValidationException ex, WebRequest request) {
    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", new java.util.Date());
    body.put("status", HttpStatus.BAD_REQUEST.value());
    body.put("message", ex.getMessage());
    body.put("error", "Validação de dados falhou.");
    body.put("path", request.getDescription(false).substring(4));
    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  // Métdo para tratar erros de Validação
  @ExceptionHandler(MethodArgumentNotValidException.class) //  Esta anotação diz ao Spring para "capturar" qualquer MethodArgumentNotValidException que seja lançada nos controladores.
  public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", new Date());
    body.put("status", HttpStatus.BAD_REQUEST.value());
    body.put("error", "Erro na validação de dados.");
    body.put("path", request.getDescription(false).substring(4));

    Map<String, String> validationErrors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach(error -> { // ex.getBindingResult().getAllErrors(): Este trecho de código obtém todos os erros de validação que ocorreram. Se mais de um campo falhou na validação, todos eles estarão aqui. forEach(error -> {...}): O código itera sobre cada erro.
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      validationErrors.put(fieldName, errorMessage);
    });

    body.put("messages", validationErrors); // Adiciona a lista de erros de validação
    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

}

Feature: Ejemplo de prueba para API

  Background:
    * url 'http://localhost:8080'

  Scenario: Crear pedido
    When method POST
    Then status 200
    And match response == {userId: 1, id: 1, title: 'sunt aut facere repellat provident occaecati excepturi optio reprehenderit', body: 'quia et suscipit\nsuscipit...'}
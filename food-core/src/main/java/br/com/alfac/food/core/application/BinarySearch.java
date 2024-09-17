package br.com.alfac.food.core.application;

public class BinarySearch {

    public static int binarySearch(int[] array, int key) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            // Se o elemento for encontrado no meio
            if (array[mid] == key) {
                return mid;
            }

            // Se o elemento for maior que o valor no meio, ignore a metade esquerda
            if (array[mid] < key) {
                left = mid + 1;
            } 
            // Se o elemento for menor que o valor no meio, ignore a metade direita
            else {
                right = mid - 1;
            }
        }

        // Retorna -1 se o elemento não for encontrado
        return -1;
    }

    public static void main(String[] args) {
        int[] array = {2, 3, 4, 10, 40};
        int key = 0;
        int result = binarySearch(array, key);

        if (result == -1) {
            System.out.println("Elemento não encontrado.");
        } else {
            System.out.println("Elemento encontrado no índice: " + result);
        }
    }


}
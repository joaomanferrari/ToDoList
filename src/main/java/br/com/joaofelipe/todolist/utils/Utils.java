package br.com.joaofelipe.todolist.utils;

import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;

public class Utils {

    public static void copyNonNullProperties(Object source, Object target) {
         BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    



    }
    //pega os nomes das propriedades que estão nulas em um objeto e retorna um array de strings com esses nomes
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);

        // pega os nomes das propriedades do objeto
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        // cria um set para armazenar os nomes das propriedades que estão nulas
        java.util.Set<String> emptyNames = new java.util.HashSet<>();

        // percorre as propriedades e verifica se estão nulas
        for(PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if(srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        // converte o set para um array de strings e retorna
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
    
}

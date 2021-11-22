package kitchenpos.application;

import kitchenpos.application.dto.ProductInformationRequest;
import kitchenpos.application.dto.ProductRequest;
import kitchenpos.application.dto.ProductResponse;
import kitchenpos.application.dto.ProductResponses;
import kitchenpos.domain.MenuProductEvent;
import kitchenpos.domain.Price;
import kitchenpos.domain.Product;
import kitchenpos.domain.repository.ProductRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ProductService(ProductRepository productRepository,
                          ApplicationEventPublisher eventPublisher) {
        this.productRepository = productRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public ProductResponse create(final ProductRequest request) {
        final Product product = productWith(request);
        return new ProductResponse(productRepository.save(product));
    }

    public ProductResponses list() {
        return new ProductResponses(productRepository.findAll());
    }

    @Transactional
    public ProductResponse update(final Long productId, final ProductInformationRequest request) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(IllegalArgumentException::new);
        product.updatePrice(request.getPrice());
        eventPublisher.publishEvent(new MenuProductEvent(product.getId(), product.getPrice()));
        return new ProductResponse(productRepository.save(product));
    }

    private Product productWith(ProductRequest request) {
        return Product.builder()
                .name(request.getName())
                .price(new Price(request.getPrice()))
                .build();
    }
}

import "./ProductCard.css"

export default function ProductCard({product, loadProducts}) {

    const handleOnDelete = async productId => {
        try {
          const response = await fetch(
            `http://localhost:8080/product/${productId}`,
            { method: "DELETE" }
          );

          if (!response.ok) throw new Error("Failed to delete");

          loadProducts(); 
        } catch (err) {
          setError(err.message);
        }
    };

    return (
        <div
          className="h-36 border rounded-lg p-4 shadow-sm bg-white relative hover:shadow-md transition"
        >
          {/* Delete button */}
          <button
            onClick={() => handleOnDelete(product.id)}
            className="absolute top-3 right-3 text-gray-500 hover:text-gray-700 hover:scale-105 transition"
          >
            ✕
          </button>

          <h3 className="text-lg font-medium text-neutral-950">{product.name}</h3>
          <p className="text-gray-600 text-sm">SKU: {product.sku}</p>

          <p className="text-sm text-gray-700 mt-2">
            Type: {product?.productType}
          </p>
        </div>
    );
}

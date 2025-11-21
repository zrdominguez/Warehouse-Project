export default function Modal({ open, onClose, title, children }) {
  if (!open) return null;

  return (
    <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 text-white">
      <div className="bg-orange-300 rounded-xl shadow-lg w-full max-w-md p-6 relative">
        <button
          onClick={onClose}
          className="absolute top-3 right-3 text-gray-500 hover:text-gray-700 hover:cursor-pointer"
        >
          ✕
        </button>

        <h2 className="text-xl font-semibold mb-4 text-center">{title}</h2>

        {children}
      </div>
    </div>
  );
}
